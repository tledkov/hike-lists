package io.github.tledkov.hikelists.data

import android.graphics.Color
import androidx.room.Dao
import io.github.tledkov.hikelists.data.entity.ItemsListEntity
import io.github.tledkov.hikelists.data.entity.RelationItemToItemsListEntity
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import io.github.tledkov.hikelists.domain.InventoryList
import io.github.tledkov.hikelists.domain.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class InventoryListRepositoryImpl(
    private val itemListsDao: ItemListsDao,
    private val relationItemToItemListsDao: RelationItemToItemListsDao,
    private val inventoryItemRepository: InventoryItemRepository,
) : InventoryListRepository {

    override suspend fun upsert(inventoryList: InventoryList): Long {
        var listId = itemListsDao.upsert(
            ItemsListEntity(
                id = inventoryList.id,
                name = inventoryList.name,
                description = inventoryList.description,
                color = inventoryList.color.toString(),
            )
        )

        if (listId == -1L) {
            listId = inventoryList.id.toLong()
        }

        // Удаляем старые связи и создаем новые
        relationItemToItemListsDao.deleteByListId(listId.toInt())
        inventoryList.items.forEach { usedItem ->
            relationItemToItemListsDao.upsert(
                RelationItemToItemsListEntity(
                    id = 0,
                    listId = listId.toInt(),
                    itemId = usedItem.item.id,
                    count = usedItem.count,
                    checked = usedItem.checked
                )
            )
        }

        return listId
    }

    override suspend fun delete(inventoryList: InventoryList) {
        itemListsDao.deleteById(inventoryList.id)
    }

    override fun getAllInventoryLists(): Flow<List<InventoryList>> {
        return itemListsDao.getAllItemsLists()
            .combine(
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

                return@combine lstMap.values.toList()
            }
    }
}