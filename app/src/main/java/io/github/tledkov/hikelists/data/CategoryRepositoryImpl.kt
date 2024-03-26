package io.github.tledkov.hikelists.data

import android.graphics.Color
import io.github.tledkov.hikelists.data.entity.CategoryEntity
import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import kotlinx.coroutines.runBlocking

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override suspend fun insert(category: Category): Long {
        return categoryDao.insert(convert(category))
    }

    override suspend fun upsert(category: Category): Long {
        return categoryDao.upsert(convert(category))
    }

    override suspend fun delete(category: Category) {
        categoryDao.deleteById(category.id)
    }

    override suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategoris().map(this::convert)
    }

    private fun convert(cat: CategoryEntity): Category {
        val color = try {
            Color.valueOf(Color.parseColor(cat.color))
        } catch (e: Exception) {
            Color.valueOf(Color.GRAY)
        }

        return Category(
            cat.id,
            cat.name,
            cat.description,
            color
        )
    }


    private fun convert(cat: Category): CategoryEntity =
        CategoryEntity(
            cat.id,
            cat.name,
            cat.description,
            cat.color.toString()
        )
}