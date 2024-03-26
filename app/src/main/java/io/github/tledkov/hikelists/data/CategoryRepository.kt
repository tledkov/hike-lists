package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem

interface CategoryRepository {
    suspend fun insert(category: Category): Long

    suspend fun upsert(category: Category): Long

    suspend fun delete(category: Category)

    suspend fun getAllCategories() : List<Category>
}