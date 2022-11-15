package com.example.booksearchapp.data.datasource

import androidx.paging.PagingSource
import com.example.booksearchapp.base.BaseDataSource
import com.example.booksearchapp.model.network.api.BookServiceApi
import com.example.booksearchapp.model.database.dao.BookDao
import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.model.network.response.BestSellerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookDataSourceImpl @Inject constructor(private val bookDao: BookDao, private val bookService: BookServiceApi): BookDataSource, BaseDataSource() {
    override suspend fun getBestSellerResult(categoryId: String): Flow<BestSellerResponse> = flow {
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