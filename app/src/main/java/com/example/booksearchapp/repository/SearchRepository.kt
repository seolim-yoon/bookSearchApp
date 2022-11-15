package com.example.booksearchapp.repository

import com.example.booksearchapp.model.database.dto.HistoryModel
import com.example.booksearchapp.model.network.response.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SearchRepository {
    // 검색 키워드로 검색한 결과를 서버에서 가져옴
    suspend fun searchBooksByName(keyword: String, page: Int) : Response<SearchResponse>

    // 저장된 검색 기록을 Room DB에서 가져옴
    suspend fun getAllHistory() : Flow<List<HistoryModel>>

    // 검색 결과를 Room DB에 저장
    suspend fun insertHistory(history: HistoryModel)

    // 검색 결과를 삭제
    suspend fun deleteHistory(keyword: String)
}