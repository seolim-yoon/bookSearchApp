package com.example.booksearchapp.model.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.model.database.dto.BestSellerModel

@Dao
interface BookDao {
    @Query("SELECT * FROM BestSeller WHERE categoryId LIKE :categoryId ORDER BY rank ASC")
    fun getAllBestSellersByCategory(categoryId: String): PagingSource<Int, BestSellerModel>

    @Query("SELECT categoryName FROM BestSeller WHERE categoryId LIKE :categoryId")
    suspend fun getBestSellersCategory(categoryId: String): List<String>

    @Query("SELECT * FROM BestSeller WHERE rank LIKE :rank + 1")
    suspend fun getSelectBestSeller(rank: Int): BestSellerModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>)
}