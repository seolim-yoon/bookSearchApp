package com.example.booksearchapp.repository

import android.app.Application
import androidx.paging.PagingSource
import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.RetrofitBuilder
import com.example.booksearchapp.data.database.AppDatabase
import com.example.booksearchapp.data.database.dao.BookDao
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.SearchResult
import io.reactivex.Single

class BookRepository(application: Application) {
    private val key = "7D32361E8C549F8E4E42943E5937CC502D866279E38FB8BF4A02E636FC45DF2B"
    private val appDatabase = AppDatabase.getInstance(application)!!
    private val bookDao: BookDao = appDatabase.bookDao()

    fun searchBooksByName(query: String, page: Int) : Single<SearchResult> = RetrofitBuilder.service.searchBooksByName(key, query, page);

    fun getBestSellerResult(categoryId: String): Single<BestSellerResult> = RetrofitBuilder.service.getBestSellerBooks(key, categoryId)

    fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>) = bookDao.insertAllBestSeller(bestSellerModels)

    fun getAllBestSellersByCategory(categoryId: String) : PagingSource<Int, BestSellerModel> = bookDao.getAllBestSellersByCategory(categoryId)

}