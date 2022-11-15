package com.example.booksearchapp.data.datasource

import androidx.paging.PagingSource
import com.example.booksearchapp.base.BaseDataSource
import com.example.booksearchapp.data.BookService
import com.example.booksearchapp.data.database.dao.BookDao
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.BestSellerResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookDataSourceImpl @Inject constructor(private val bookDao: BookDao, private val bookService: BookService): BookDataSource, BaseDataSource() {
    override suspend fun getBestSellerResult(categoryId: String): Flow<BestSellerResult> = flow {
        emit(bookService.getBestSellerBooks(key, categoryId))
    }

    override suspend fun getBestSellersCategory(categoryId: String) : Flow<List<String>> = flow {
        emit(bookDao.getBestSellersCategory(categoryId))
    }

    override suspend fun getSelectBestSeller(rank: Int): Flow<BestSellerModel> = flow {
        emit(bookDao.getSelectBestSeller(rank))
    }

    override suspend fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>): Flow<Unit> = flow {
        emit(bookDao.insertAllBestSeller(bestSellerModels))
    }

    override fun getAllBestSellersByCategory(categoryId: String) : PagingSource<Int, BestSellerModel> = bookDao.getAllBestSellersByCategory(categoryId)
}