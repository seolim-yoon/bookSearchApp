package com.example.booksearchapp.data

import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.response.SearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("api/search.api?output=json")
    fun searchBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String,
        @Query("start") Page: Int
    ): Single<SearchResult>

    @GET("api/bestSeller.api?output=json")
    fun getBestSellerBooks(
        @Query("key") apiKey: String,
        @Query("categoryId") categoryId: String,
    ) : Single<BestSellerResult>
}