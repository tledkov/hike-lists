package io.github.tledkov.hikelists

import android.app.Application
import io.github.tledkov.hikelists.data.CategoryRepository
import io.github.tledkov.hikelists.data.CategoryRepositoryImpl
import io.github.tledkov.hikelists.data.InventoryItemRepository
import io.github.tledkov.hikelists.data.InventoryItemRepositoryImpl
import io.github.tledkov.hikelists.data.HikeListsDatabase
import kotlinx.coroutines.runBlocking

class App : Application() {
    private lateinit var database: HikeListsDatabase

    lateinit var inventoryItemRepository: InventoryItemRepository
    lateinit var categoryRepository: CategoryRepository

    override fun onCreate() {
        super.onCreate()

        database = HikeListsDatabase.buildDatabase(applicationContext)
        database.init()

        categoryRepository = CategoryRepositoryImpl(database.categoryDao())

        runBlocking {
            inventoryItemRepository = InventoryItemRepositoryImpl(
                database.itemDao(),
                categoryRepository.getAllCategories().toSet()
            )
        }
    }

    companion object {
    }
}