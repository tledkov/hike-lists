package io.github.tledkov.hikelists.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import io.github.tledkov.hikelists.data.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT 1")
    suspend fun init(): Int

    @Insert
    suspend fun insert(categoryEntity: CategoryEntity): Long

    @Upsert
    suspend fun upsert(categoryEntity: CategoryEntity): Long

    @Query("SELECT * FROM ${CategoryEntity.TABLE_NAME}")
    suspend fun getAllCategoris(): List<CategoryEntity>

    @Query("DELETE FROM ${CategoryEntity.TABLE_NAME} WHERE ${CategoryEntity.ID} = :id")
    suspend fun deleteById(id: Int)
}