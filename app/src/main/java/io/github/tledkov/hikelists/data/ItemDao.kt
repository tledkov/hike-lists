package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import io.github.tledkov.hikelists.data.entity.ItemEntity

@Dao
interface ItemDao {
    @Query("SELECT 1")
    suspend fun init() : Int

    @Insert
    suspend fun insert(itemEntity: ItemEntity): Long

    @Update
    suspend fun update(itemEntity: ItemEntity)

    @Upsert
    suspend fun upsert(itemEntity: ItemEntity)

    @Query("DELETE FROM ${ItemEntity.TABLE_NAME} WHERE ${ItemEntity.ID} = :id")
    suspend fun deleteById(id: Int)


    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME}")
    suspend fun getAllItems(): List<ItemEntity>


    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME} as i WHERE i.categoryId = :categoryId")
    suspend fun getItems(categoryId: Int): List<ItemEntity>

    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME} as i WHERE i.categoryId IS NULL")
    suspend fun getItemsWithoutCategory(): List<ItemEntity>
}