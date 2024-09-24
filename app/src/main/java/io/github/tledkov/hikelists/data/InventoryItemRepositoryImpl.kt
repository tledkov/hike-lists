package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import io.github.tledkov.hikelists.domain.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryItemRepositoryImpl(
    private val itemDao: ItemDao,
    categories: Set<Category>
) : InventoryItemRepository {

    private val categoryIdMap: Map<Int, Category> = categories.associateBy { it.id }

    override suspend fun insert(item: InventoryItem): Long {
        return itemDao.insert(convert(item))
    }

    override suspend fun upsert(item: InventoryItem) {
        itemDao.upsert(convert(item))
    }

    override suspend fun delete(item: InventoryItem) {
        itemDao.deleteById(item.id)
    }


    override fun getAllItems(): Flow<List<InventoryItem>> {
        return itemDao.getAllItems().map { it.map(this::convert) }
    }

    override suspend fun getItems(category: Category): Flow<List<InventoryItem>> {
        return itemDao.getItems(category.id).map { it.map(this::convert) }
    }

    override suspend fun getItemsWithoutCategory(): Flow<List<InventoryItem>> {
        return itemDao.getItemsWithoutCategory().map { it.map(this::convert) }
    }

    private fun convert(item: ItemEntity): InventoryItem =
        InventoryItem(
            item.id,
            category = item.categoryId?.let {
                categoryIdMap[it]
            },
            Weight.from(item.weightGr),
            item.name,
            item.description,
            ""
        )

    private fun convert(item: InventoryItem): ItemEntity =
        ItemEntity(
            item.id,
            item.category?.id,
            item.weight.value(),
            item.name,
            item.description,
            ""
        )
}