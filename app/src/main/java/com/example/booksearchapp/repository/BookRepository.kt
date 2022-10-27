package com.example.booksearchapp.repository

import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.BestSellerResult
import com.skydoves.sandwich.ApiResponse
import io.reactivex.Completable
import io.reactivex.Single

interface BookRepository {
    // 선택한 카테고리의 베스트셀러 서버에서 가져옴
    fun getBestSellerResult(categoryId: String, start: Int, maxResults: Int): ApiResponse<BestSellerResult>

    // 서버에서 가져온 베스트셀러 리스트를 Room DB에 insert
    fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>) : Completable

    // 선택한 카테고리별로 Room DB에서 베스트셀러 리스트 가져옴
    fun getAllBestSellersByCategory(categoryId: String) : Single<List<String>>

    // 선택한 카테고리 id로 카테고리 이름 가져옴 ex) id = 101, name = 국내도서>소설
    fun getBestSellersCategory(categoryId: String) : Single<List<String>>
}