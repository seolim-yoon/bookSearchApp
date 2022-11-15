package com.example.booksearchapp.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.model.database.dto.HistoryModel

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History")
    suspend fun getAllHistory(): List<HistoryModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryModel)

    @Query("DELETE FROM History WHERE keyword = :keyword")
    suspend fun deleteHistory(keyword: String)
}