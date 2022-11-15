package com.example.booksearchapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.data.database.model.BestSellerModel
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface BookDao {
    @Query("SELECT * FROM BestSeller WHERE categoryId LIKE :categoryId ORDER BY rank ASC")
    fun getAllBestSellersByCategory(categoryId: String): PagingSource<Int, BestSellerModel>

    @Query("SELECT categoryName FROM BestSeller WHERE categoryId LIKE :categoryId")
    suspend fun getBestSellersCategory(categoryId: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>)
}