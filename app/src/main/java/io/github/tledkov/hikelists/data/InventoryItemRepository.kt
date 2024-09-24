package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import kotlinx.coroutines.flow.Flow

interface InventoryItemRepository {

    suspend fun insert(item: InventoryItem): Long

    suspend fun upsert(item: InventoryItem)

    suspend fun delete(item: InventoryItem)

    suspend fun getItems(category: Category) : Flow<List<InventoryItem>>
    fun getAllItems(): Flow<List<InventoryItem>>
    suspend fun getItemsWithoutCategory(): Flow<List<InventoryItem>>
}