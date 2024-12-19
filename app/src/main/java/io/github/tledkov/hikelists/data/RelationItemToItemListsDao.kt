package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.tledkov.hikelists.data.entity.RelationItemToItemsListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RelationItemToItemListsDao {

    @Upsert
    suspend fun upsert(relationItemToItemsListEntity: RelationItemToItemsListEntity): Long

    @Query("SELECT * FROM ${RelationItemToItemsListEntity.TABLE_NAME}")
    fun getAllItemsLists(): Flow<List<RelationItemToItemsListEntity>>

    @Query("DELETE FROM ${RelationItemToItemsListEntity.TABLE_NAME} WHERE ${RelationItemToItemsListEntity.ID} = :id")
    suspend fun deleteById(id: Int)
}