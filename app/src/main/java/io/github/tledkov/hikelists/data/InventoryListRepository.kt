package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import io.github.tledkov.hikelists.domain.InventoryList
import kotlinx.coroutines.flow.Flow

interface InventoryListRepository {

    suspend fun upsert(inventoryList: InventoryList): Long

    suspend fun delete(inventoryList: InventoryList)

    fun getAllInventoryLists() : Flow<List<InventoryList>>
}