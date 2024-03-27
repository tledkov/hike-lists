package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import io.github.tledkov.hikelists.domain.Weight

class InventoryItemRepositoryImpl(
    private val itemDao: ItemDao,
    private val categories: Set<Category>
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


    override suspend fun getAllItems(): List<InventoryItem> {
        return itemDao.getAllItems().map(this::convert)
    }

    override suspend fun getItems(category: Category): List<InventoryItem> {
        return mutableListOf()
    }

    override suspend fun getPersonsWithoutCategory(): List<InventoryItem> {
        return mutableListOf()
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