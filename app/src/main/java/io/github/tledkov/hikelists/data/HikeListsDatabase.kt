package io.github.tledkov.hikelists.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.data.entity.CategoryEntity
import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.data.entity.RelationItemToItemsListEntity
import io.github.tledkov.hikelists.data.entity.ItemsListEntity
import kotlinx.coroutines.runBlocking

@Database(
    entities = [
        ItemEntity::class,
        CategoryEntity::class,
        ItemsListEntity::class,
        RelationItemToItemsListEntity::class
    ],
    version = 1
)
abstract class HikeListsDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun categoryDao(): CategoryDao

    abstract fun itemListsDao(): ItemListsDao

    abstract fun relationItemToItemListsDao(): RelationItemToItemListsDao

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
                String.format(
                    "#%06X",
                    0xFFFFFF and (context.resources.getColor(R.color.category_backpack))
                )
            )

            val catToSleep = CategoryEntity(
                2,
                context.resources.getString(R.string.category_to_sleep),
                "",
                String.format(
                    "#%06X",
                    0xFFFFFF and (context.resources.getColor(R.color.category_to_sleep))
                )
            )

            db.categoryDao().upsert(catBackpack)
            db.categoryDao().upsert(catToSleep)

            db.categoryDao().upsert(
                CategoryEntity(
                    3,
                    context.resources.getString(R.string.category_primary),
                    "",
                    String.format(
                        "#%06X",
                        0xFFFFFF and (context.resources.getColor(R.color.category_primary))
                    )
                )
            )

            db.categoryDao().upsert(
                CategoryEntity(
                    4,
                    context.resources.getString(R.string.category_secondary),
                    "",
                    String.format(
                        "#%06X",
                        0xFFFFFF and (context.resources.getColor(R.color.category_secondary))
                    )
                )
            )

            db.categoryDao().upsert(
                CategoryEntity(
                    5,
                    context.resources.getString(R.string.category_clothing),
                    "",
                    String.format(
                        "#%06X",
                        0xFFFFFF and (context.resources.getColor(R.color.category_clothing))
                    )
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

            for (i in 0..20) {
                db.itemDao().upsert(
                    ItemEntity(
                        0,
                        1,
                        1000 + i,
                        "Test item $i",
                        "",
                        ""
                    )
                )
            }

            db.itemListsDao().upsert(
                ItemsListEntity(1, "Winter staff", "WInter trip staff", "")
            )

            db.itemListsDao().upsert(
                ItemsListEntity(2, "Summer packraft solo", "Summer packraft solo trip staff", "")
            )

            db.relationItemToItemListsDao().upsert(
                RelationItemToItemsListEntity(
                    1, 1, 1, 1, false,
                )
            )

            db.relationItemToItemListsDao().upsert(
                RelationItemToItemsListEntity(
                    2, 2, 1, 2, false,
                )
            )

            db.relationItemToItemListsDao().upsert(
                RelationItemToItemsListEntity(
                    3, 3, 1, 1, false,
                )
            )

            db.relationItemToItemListsDao().upsert(
                RelationItemToItemsListEntity(
                    4, 4, 2, 1, false,
                )
            )

            db.relationItemToItemListsDao().upsert(
                RelationItemToItemsListEntity(
                    5, 1, 2, 1, true,
                )
            )
        }
    }
}

