package com.example.booksearchapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.booksearchapp.data.database.model.HistoryModel

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History")
    fun getAllHistory(): List<HistoryModel>

}