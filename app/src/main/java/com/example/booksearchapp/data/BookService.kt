package com.example.booksearchapp.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey: String,
        @Query("query") keyword: String
    ): Single<SearchResult>

    @GET("api/bestSeller.api?categoryId=100&output=json")
    fun getBestSellerBooks(
        @Query("key") apiKey: String
    ) : Single<BestSellerResult>

//    @GET("api/recommend.api?categoryId=100&output=json")
//    fun getRecommendBooks(
//        @Query("key") apiKey: String
//    ) : Single<Recommend>
}