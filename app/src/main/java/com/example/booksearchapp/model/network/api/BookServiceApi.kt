package com.example.booksearchapp.model.network.api

import com.example.booksearchapp.model.network.response.BestSellerResponse
import com.example.booksearchapp.model.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookServiceApi {
    @GET("api/search.api?output=json")
    suspend fun searchBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String,
        @Query("start") Page: Int
    ): Response<SearchResponse>

    @GET("api/bestSeller.api?output=json")
    suspend fun getBestSellerBooks(
        @Query("key") apiKey: String,
        @Query("categoryId") categoryId: String,
    ) : BestSellerResponse
}