package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.data.entity.ItemId

class ItemRepositoryImpl(private val personDao: ItemDao) : ItemRepository {
    override suspend fun insert(itemEntity: ItemEntity) {
        personDao.insert(itemEntity)
    }

    override suspend fun getAllPersons() = personDao.getAllItems()

    override suspend fun deleteById(id: ItemId) {
        personDao.deleteById(id)
    }
}