package io.github.tledkov.hikelists.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.data.entity.CategoryEntity
import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.data.entity.ItemItemsList
import io.github.tledkov.hikelists.data.entity.ItemsListEntity
import kotlinx.coroutines.runBlocking

@Database(
    entities = [
        ItemEntity::class,
        CategoryEntity::class,
        ItemsListEntity::class,
        ItemItemsList::class
    ],
    version = 1
)
abstract class HikeListsDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
    abstract fun categoryDao(): CategoryDao

    fun init() {
        runBlocking{
            itemDao().init()
            categoryDao().init()
        }
    }

    companion object {
        private const val DATABASE_NAME = "hiking-list.db"

        @Volatile
        private var INSTANCE: HikeListsDatabase? = null

        fun getInstance(context: Context): HikeListsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        fun buildDatabase(context: Context): HikeListsDatabase {
            return Room
                .databaseBuilder(context, HikeListsDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            runBlocking {
                                initialPopulate(getInstance(context), context)
                            }
                        }
                    }
                })
                .build()
        }

        suspend fun initialPopulate(db: HikeListsDatabase, context: Context) {
            val catBackpack = CategoryEntity(
                1,
                context.resources.getString(R.string.category_backpack),
                "",
                context.resources.getColor(R.color.category_backpack).toString()
            )

            val catToSleep = CategoryEntity(
                2,
                context.resources.getString(R.string.category_to_sleep),
                "",
                context.resources.getColor(R.color.category_to_sleep).toString()
            )

            db.categoryDao().upsert(catBackpack)
            db.categoryDao().upsert(catToSleep)

            db.categoryDao().upsert(
                CategoryEntity(
                    3,
                    context.resources.getString(R.string.category_primary),
                    "",
                    context.resources.getColor(R.color.category_primary).toString()
                )
            )

            db.categoryDao().upsert(
                CategoryEntity(
                    4,
                    context.resources.getString(R.string.category_secondary),
                    "",
                    context.resources.getColor(R.color.category_secondary).toString()
                )
            )

            db.categoryDao().upsert(
                CategoryEntity(
                    5,
                    context.resources.getString(R.string.category_clothing),
                    "",
                    context.resources.getColor(R.color.category_clothing).toString()
                )
            )

            // --- DEBUG
            db.itemDao().upsert(
                ItemEntity(
                    1,
                    null,
                    1010,
                    "Backpack KVN",
                    "Backpack KVN without top",
                    ""
                )
            )

            db.itemDao().upsert(
                ItemEntity(
                    2,
                    1,
                    1150,
                    "Backpack KVN with top",
                    "Backpack KVN with top",
                    ""
                )
            )

            db.itemDao().upsert(
                ItemEntity(
                    3,
                    1,
                    450,
                    "Waterpack (green)",
                    "Splav",
                    ""
                )
            )

            db.itemDao().upsert(
                ItemEntity(
                    4,
                    2,
                    1450,
                    "Sleeping back (orange)",
                    "Redfox",
                    ""
                )
            )

            db.itemDao().upsert(
                ItemEntity(
                    5,
                    2,
                    535,
                    "AegisMax nano",
                    "Redfox",
                    ""
                )
            )

            db.itemDao().upsert(
                ItemEntity(
                    6,
                    2,
                    1935,
                    "Sleeping bag King",
                    "",
                    ""
                )
            )
        }
    }
}

