package com.example.booksearchapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.data.database.model.HistoryModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History")
    fun getAllHistory(): Single<List<HistoryModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: HistoryModel): Completable

    @Query("DELETE FROM History WHERE keyword = :keyword")
    fun deleteHistory(keyword: String): Completable
}