package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun insert(category: Category): Long

    suspend fun upsert(category: Category): Long

    suspend fun delete(category: Category)

    fun getAllCategories() : Flow<List<Category>>
}