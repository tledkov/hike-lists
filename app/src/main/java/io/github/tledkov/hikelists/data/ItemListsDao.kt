package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.tledkov.hikelists.data.entity.ItemsListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemListsDao {

    @Upsert
    suspend fun upsert(itemsList: ItemsListEntity): Long

    @Query("SELECT * FROM ${ItemsListEntity.TABLE_NAME}")
    fun getAllItemsLists(): Flow<List<ItemsListEntity>>

    @Query("DELETE FROM ${ItemsListEntity.TABLE_NAME} WHERE ${ItemsListEntity.ID} = :id")
    suspend fun deleteById(id: Int)
}