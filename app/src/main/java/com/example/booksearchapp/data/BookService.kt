package com.example.booksearchapp.data

import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.response.SearchResult
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("api/search.api?output=json")
    suspend fun searchBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String,
        @Query("start") Page: Int
    ): Response<SearchResult>

    @GET("api/bestSeller.api?output=json")
    suspend fun getBestSellerBooks(
        @Query("key") apiKey: String,
        @Query("categoryId") categoryId: String,
    ) : BestSellerResult
}