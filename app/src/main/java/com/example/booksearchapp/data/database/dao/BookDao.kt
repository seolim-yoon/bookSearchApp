package com.example.booksearchapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.data.database.model.BestSellerModel

@Dao
interface BookDao {
    @Query("SELECT * FROM BestSeller WHERE categoryId LIKE :categoryId ORDER BY rank ASC")
    fun getAllBestSellersByCategory(categoryId: String): PagingSource<Int, BestSellerModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>)
}