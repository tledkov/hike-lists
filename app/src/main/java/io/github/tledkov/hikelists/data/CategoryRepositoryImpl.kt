package io.github.tledkov.hikelists.data

import android.graphics.Color
import io.github.tledkov.hikelists.data.entity.CategoryEntity
import io.github.tledkov.hikelists.domain.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { it.map { convert(it) } }
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