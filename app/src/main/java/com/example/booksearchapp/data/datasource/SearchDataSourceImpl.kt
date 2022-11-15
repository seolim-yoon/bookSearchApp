package com.example.booksearchapp.data.datasource

import com.example.booksearchapp.base.BaseDataSource
import com.example.booksearchapp.data.BookService
import com.example.booksearchapp.data.database.dao.HistoryDao
import com.example.booksearchapp.data.database.model.HistoryModel
import com.example.booksearchapp.data.response.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(private val historyDao: HistoryDao, private val bookService: BookService): SearchDataSource, BaseDataSource() {

    override suspend fun searchBooksByName(keyword: String, page: Int) : Response<SearchResult> = bookService.searchBooksByName(key, keyword, page)

    override suspend fun getAllHistory() : Flow<List<HistoryModel>> = flow {
        emit(historyDao.getAllHistory())
    }

    override suspend fun insertHistory(history: HistoryModel) = historyDao.insertHistory(history)

    override suspend fun deleteHistory(keyword: String) = historyDao.deleteHistory(keyword)
}