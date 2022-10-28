package com.example.booksearchapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.data.database.model.BestSellerModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BookDao {
    @Query("SELECT * FROM BestSeller WHERE categoryId LIKE :categoryId ORDER BY rank ASC")
    fun getAllBestSellersByCategory(categoryId: String): PagingSource<Int, BestSellerModel>

    @Query("SELECT categoryName FROM BestSeller WHERE categoryId LIKE :categoryId")
    fun getBestSellersCategory(categoryId: String): Single<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>) : Completable
}