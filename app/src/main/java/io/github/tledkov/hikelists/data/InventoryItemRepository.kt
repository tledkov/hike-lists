package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem

interface InventoryItemRepository {

    suspend fun insert(inventoryItem: InventoryItem): Long

    suspend fun upsert(item: InventoryItem)

    suspend fun delete(item: InventoryItem)

    suspend fun getItems(category: Category) : List<InventoryItem>
    suspend fun getAllItems(): List<InventoryItem>
    suspend fun getPersonsWithoutCategory(): List<InventoryItem>
}