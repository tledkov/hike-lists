package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.tledkov.hikelists.data.entity.ItemEntity

@Dao
interface ItemDao {

    @Insert
    suspend fun insert(itemEntity: ItemEntity)

    @Query("SELECT * FROM ${ItemEntity.TABLE_NAME}")
    suspend fun getAllItems(): List<ItemEntity>

    @Query("DELETE FROM ${ItemEntity.TABLE_NAME} WHERE ${ItemEntity.ID} = :id")
    suspend fun deleteById(id: Int)
}