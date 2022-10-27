package com.example.booksearchapp.repository

import com.example.booksearchapp.base.BaseRepository
import com.example.booksearchapp.data.BookService
import com.example.booksearchapp.data.database.dao.HistoryDao
import com.example.booksearchapp.data.database.model.HistoryModel
import com.example.booksearchapp.data.response.SearchResult
import com.skydoves.sandwich.ApiResponse
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val historyDao: HistoryDao, private val bookService: BookService): SearchRepository, BaseRepository() {

    override fun searchBooksByName(keyword: String, page: Int) : ApiResponse<SearchResult> = bookService.searchBooksByName(key, keyword, page)

    override fun getAllHistory() : Single<List<HistoryModel>> = historyDao.getAllHistory()

    override fun insertHistory(history: HistoryModel) : Completable = historyDao.insertHistory(history)

    override fun deleteHistory(keyword: String) : Completable = historyDao.deleteHistory(keyword)
}