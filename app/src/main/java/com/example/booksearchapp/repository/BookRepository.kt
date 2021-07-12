package com.example.booksearchapp.repository

import android.app.Application
import androidx.paging.PagingSource
import com.example.booksearchapp.data.BestSellerResult
import com.example.booksearchapp.data.RetrofitBuilder
import com.example.booksearchapp.data.database.AppDatabase
import com.example.booksearchapp.data.database.BookDao
import com.example.booksearchapp.ui.model.BaseModel
import com.example.booksearchapp.ui.model.BestSellerModel
import io.reactivex.Single

class BookRepository(application: Application) {
    private val key = "7D32361E8C549F8E4E42943E5937CC502D866279E38FB8BF4A02E636FC45DF2B"
    private val appDatabase = AppDatabase.getInstance(application)!!
    private val bookDao: BookDao = appDatabase.bookDao()


    fun getBestSellerResult(): Single<BestSellerResult> = RetrofitBuilder.service.getBestSellerBooks(key)

    fun insertBestSellers(bestSellerModel: BestSellerModel) = bookDao.insertBestSeller(bestSellerModel)

    fun getAllBestSellers() : PagingSource<Int, BestSellerModel> = bookDao.getAllBestSellers()

}