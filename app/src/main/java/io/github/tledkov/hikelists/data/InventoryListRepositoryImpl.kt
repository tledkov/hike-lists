package io.github.tledkov.hikelists.data

import android.graphics.Color
import io.github.tledkov.hikelists.data.entity.ItemsListEntity
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import io.github.tledkov.hikelists.domain.InventoryList
import io.github.tledkov.hikelists.domain.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip

class InventoryListRepositoryImpl(
    private val itemListsDao: ItemListsDao,
    private val relationItemToItemListsDao: RelationItemToItemListsDao,
    private val inventoryItemRepository: InventoryItemRepository,
) : InventoryListRepository {

    override suspend fun upsert(inventoryList: InventoryList): Long {
        return itemListsDao.upsert(
            ItemsListEntity(
                id = inventoryList.id,
                name = inventoryList.name,
                description = inventoryList.description,
                color = inventoryList.color.toString(),
            )
        )
    }

    override suspend fun delete(inventoryList: InventoryList) {
        itemListsDao.deleteById(inventoryList.id)
    }

    override fun getAllInventoryLists(): Flow<List<InventoryList>> {
        return itemListsDao.getAllItemsLists()
            .zip(
                relationItemToItemListsDao.getAllItemsLists()
            ) { list, items ->
                val lstMap: Map<Int, InventoryList> = list
                    .map {
                        val color = try {
                            Color.valueOf(Color.parseColor(it.color))
                        } catch (e: Exception) {
                            Color.valueOf(Color.GRAY)
                        }

                        InventoryList(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            items = mutableListOf(),
                            color = color,
                        )
                    }
                    .associateBy { it.id }

                items.forEach {
                    val item = inventoryItemRepository.getById(it.itemId)

                    lstMap[it.listId]?.items?.add(
                        InventoryList.UsedItem(
                            item = item,
                            count = it.count,
                            checked = it.checked,
                        )
                    )
                }

                return@zip lstMap.values.toList()
            }
    }
}