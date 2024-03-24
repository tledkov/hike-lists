package io.github.tledkov.hikelists.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.tledkov.hikelists.data.entity.CategoryEntity
import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.data.entity.ItemCategory
import io.github.tledkov.hikelists.data.entity.ItemItemsList
import io.github.tledkov.hikelists.data.entity.ItemsListEntity

@Database(
    entities = [
        ItemEntity::class,
        CategoryEntity::class,
        ItemsListEntity::class,
        ItemCategory::class,
        ItemItemsList::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        fun buildDatabase(context: Context, dbName: String): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
        }
    }

}