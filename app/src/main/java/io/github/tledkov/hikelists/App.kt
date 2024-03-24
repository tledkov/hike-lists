package io.github.tledkov.hikelists

import android.app.Application
import io.github.tledkov.hikelists.data.ItemRepository
import io.github.tledkov.hikelists.data.ItemRepositoryImpl
import io.github.tledkov.hikelists.data.AppDatabase

class App : Application() {
    private lateinit var database: AppDatabase

    lateinit var repository: ItemRepository

    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.buildDatabase(
            applicationContext,
            DATABASE_NAME
        )

        repository = ItemRepositoryImpl(database.itemDao())
    }

    companion object {
        private const val DATABASE_NAME = "hiking-list.db"
    }
}