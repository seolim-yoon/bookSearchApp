package com.example.booksearchapp.data.datasource

import androidx.paging.PagingSource
import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.model.network.response.BestSellerResponse
import kotlinx.coroutines.flow.Flow

interface BookDataSource {
    suspend fun getBestSellerResult(categoryId: String): Flow<BestSellerResponse>

    suspend fun getBestSellersCategory(categoryId: String): Flow<List<String>>

    suspend fun getSelectBestSeller(rank: Int): Flow<BestSellerModel>

    suspend fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>): Flow<Unit>

    fun getAllBestSellersByCategory(categoryId: String): PagingSource<Int, BestSellerModel>
}