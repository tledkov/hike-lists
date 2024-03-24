package io.github.tledkov.hikelists.data

import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.data.entity.ItemId

interface ItemRepository {

    suspend fun insert(itemEntity: ItemEntity)

    suspend fun getAllPersons(): List<ItemEntity>

    suspend fun deleteById(id: ItemId)
}