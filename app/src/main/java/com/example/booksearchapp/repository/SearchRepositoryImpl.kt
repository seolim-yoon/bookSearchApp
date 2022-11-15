package com.example.booksearchapp.repository

import com.example.booksearchapp.data.database.model.HistoryModel
import com.example.booksearchapp.data.datasource.SearchDataSource
import com.example.booksearchapp.data.response.SearchResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDataSource: SearchDataSource): SearchRepository {

    override suspend fun searchBooksByName(keyword: String, page: Int) : Response<SearchResult> = searchDataSource.searchBooksByName(keyword, page)

    override suspend fun getAllHistory() : Flow<List<HistoryModel>> = searchDataSource.getAllHistory()

    override suspend fun insertHistory(history: HistoryModel) = searchDataSource.insertHistory(history)

    override suspend fun deleteHistory(keyword: String) = searchDataSource.deleteHistory(keyword)
}