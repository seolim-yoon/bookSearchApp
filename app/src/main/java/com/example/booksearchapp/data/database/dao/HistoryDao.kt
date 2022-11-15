package com.example.booksearchapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.data.database.model.HistoryModel
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History")
    suspend fun getAllHistory(): List<HistoryModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryModel)

    @Query("DELETE FROM History WHERE keyword = :keyword")
    suspend fun deleteHistory(keyword: String)
}