package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.tledkov.hikelists.data.entity.RelationItemToItemsListEntity
import io.github.tledkov.hikelists.data.entity.RelationItemToItemsListEntity.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface RelationItemToItemListsDao {

    @Upsert
    suspend fun upsert(relationItemToItemsListEntity: RelationItemToItemsListEntity): Long

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllItemsLists(): Flow<List<RelationItemToItemsListEntity>>

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM $TABLE_NAME WHERE listId = :listId")
    suspend fun deleteByListId(listId: Int)
}