package com.example.booksearchapp.data

import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.response.SearchResult
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("api/search.api?output=json")
    fun searchBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String,
        @Query("start") Page: Int
    ): ApiResponse<SearchResult>

    @GET("api/bestSeller.api?output=json")
    fun getBestSellerBooks(
        @Query("key") apiKey: String,
        @Query("categoryId") categoryId: String,
        @Query("start") start: Int,
        @Query("maxResults") maxResults: Int
    ) : ApiResponse<BestSellerResult>
}