package com.example.booksearchapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.ui.model.BestSellerModel

@Dao
interface BookDao {
    @Query("SELECT * FROM BestSeller ORDER BY rank ASC")
    fun getAllBestSellers(): PagingSource<Int, BestSellerModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBestSeller(bestSellerModel: BestSellerModel)
}