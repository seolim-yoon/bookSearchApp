package com.example.booksearchapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.booksearchapp.data.database.model.BaseModel
import com.example.booksearchapp.data.database.model.BestSellerModel

@Dao
interface BookDao {
    @Query("SELECT * FROM BestSeller ORDER BY rank ASC")
    fun getAllBestSellers(): PagingSource<Int, BestSellerModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>)
}