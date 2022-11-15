package com.example.booksearchapp.data.datasource

import androidx.paging.PagingSource
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.BestSellerResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookDataSource {
    suspend fun getBestSellerResult(categoryId: String): Flow<BestSellerResult>

    suspend fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>): Flow<Unit>

    fun getAllBestSellersByCategory(categoryId: String): PagingSource<Int, BestSellerModel>

    suspend fun getBestSellersCategory(categoryId: String): Flow<List<String>>
}