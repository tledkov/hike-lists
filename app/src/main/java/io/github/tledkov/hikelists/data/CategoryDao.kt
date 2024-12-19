package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import io.github.tledkov.hikelists.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(categoryEntity: CategoryEntity): Long

    @Upsert
    suspend fun upsert(categoryEntity: CategoryEntity): Long

    @Query("SELECT * FROM ${CategoryEntity.TABLE_NAME}")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("DELETE FROM ${CategoryEntity.TABLE_NAME} WHERE ${CategoryEntity.ID} = :id")
    suspend fun deleteById(id: Int)
}