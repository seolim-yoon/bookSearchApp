package com.example.booksearchapp.repository

import androidx.paging.PagingSource
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.BestSellerResult
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookRepository {
    val domesticList: ArrayList<String>
    val foreignList: ArrayList<String>
    val recordList: ArrayList<String>
    val dvdList: ArrayList<String>

    // 선택한 카테고리의 베스트셀러 서버에서 가져옴
    suspend fun getBestSellerResult(categoryId: String): Flow<BestSellerResult>

    // 선택한 카테고리 id로 카테고리 이름 가져옴 ex) id = 101, name = 국내도서>소설
    suspend fun getBestSellersCategory(categoryId: String) : Flow<List<String>>

    suspend fun getSelectBestSeller(rank: Int): Flow<BestSellerModel>

    // 서버에서 가져온 베스트셀러 리스트를 Room DB에 insert
    suspend fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>): Flow<Unit>

    // 선택한 카테고리별로 Room DB에서 베스트셀러 리스트 가져옴
    fun getAllBestSellersByCategory(categoryId: String) : PagingSource<Int, BestSellerModel>
}