package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.tledkov.hikelists.data.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Upsert
    suspend fun upsert(itemEntity: ItemEntity)

    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME} WHERE ${ItemEntity.ID} = :id")
    suspend fun getById(id: Int): ItemEntity

    @Query("DELETE FROM ${ItemEntity.TABLE_NAME} WHERE ${ItemEntity.ID} = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME}")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME} as i WHERE i.categoryId = :categoryId")
    fun getItems(categoryId: Int): Flow<List<ItemEntity>>

    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME} as i WHERE i.categoryId IS NULL")
    fun getItemsWithoutCategory(): Flow<List<ItemEntity>>
}