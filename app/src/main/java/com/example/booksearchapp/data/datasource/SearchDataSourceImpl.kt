package com.example.booksearchapp.data.datasource

import com.example.booksearchapp.base.BaseDataSource
import com.example.booksearchapp.model.network.api.BookServiceApi
import com.example.booksearchapp.model.database.dao.HistoryDao
import com.example.booksearchapp.model.database.dto.HistoryModel
import com.example.booksearchapp.model.network.response.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(private val historyDao: HistoryDao, private val bookService: BookServiceApi): SearchDataSource, BaseDataSource() {

    override suspend fun searchBooksByName(keyword: String, page: Int) : Response<SearchResponse> = bookService.searchBooksByName(key, keyword, page)

    override suspend fun getAllHistory() : Flow<List<HistoryModel>> = flow {
        emit(historyDao.getAllHistory())
    }

    override suspend fun insertHistory(history: HistoryModel) = historyDao.insertHistory(history)

    override suspend fun deleteHistory(keyword: String) = historyDao.deleteHistory(keyword)
}