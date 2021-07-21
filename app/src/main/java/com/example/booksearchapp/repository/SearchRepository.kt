package com.example.booksearchapp.repository

import android.app.Application
import com.example.booksearchapp.base.BaseRepository
import com.example.booksearchapp.data.RetrofitBuilder
import com.example.booksearchapp.data.database.dao.HistoryDao
import com.example.booksearchapp.data.database.model.HistoryModel
import com.example.booksearchapp.data.response.SearchResult
import io.reactivex.Completable
import io.reactivex.Single

class SearchRepository(application: Application) : BaseRepository(application){
    private val historyDao: HistoryDao = appDatabase.historyDao()

    // 검색 키워드로 검색한 결과를 서버에서 가져옴
    fun searchBooksByName(keyword: String, page: Int) : Single<SearchResult> = RetrofitBuilder.service.searchBooksByName(key, keyword, page);

    // 저장된 검색 기록을 Room DB에서 가져옴
    fun getAllHistory() : Single<List<HistoryModel>> = historyDao.getAllHistory()

    // 검색 결과를 Room DB에 저장
    fun insertHistory(history: HistoryModel) : Completable = historyDao.insertHistory(history)

    // 검색 결과를 삭제
    fun deleteHistory(keyword: String) : Completable = historyDao.deleteHistory(keyword)
}