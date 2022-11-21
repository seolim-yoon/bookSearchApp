package com.example.booksearchapp.repository

import androidx.paging.PagingSource
import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.model.network.response.BestSellerResponse
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    val domesticList: ArrayList<String>
    val foreignList: ArrayList<String>
    val recordList: ArrayList<String>
    val dvdList: ArrayList<String>

    // 선택한 카테고리의 베스트셀러 서버에서 가져옴
    suspend fun getBestSellerResult(categoryId: String): Flow<BestSellerResponse>

    suspend fun getSelectBestSeller(rank: Int): Flow<BestSellerModel>

    // 서버에서 가져온 베스트셀러 리스트를 Room DB에 insert
    suspend fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>)

    // 선택한 카테고리별로 Room DB에서 베스트셀러 리스트 가져옴
    fun getAllBestSellersByCategory(categoryId: String) : PagingSource<Int, BestSellerModel>
}