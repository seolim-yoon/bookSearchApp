package com.example.booksearchapp.feature.search

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.data.paging.SearchBookPagingSource
import com.example.booksearchapp.model.database.dto.HistoryModel
import com.example.booksearchapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : BaseViewModel() {

    private val _searchKeyword = MutableStateFlow( "")
    val searchKeyword: StateFlow<String>
        get() = _searchKeyword

    private val _isShowHistory = MutableStateFlow(false)
    val isShowHistory: StateFlow<Boolean>
        get() = _isShowHistory

    private val _historyList = MutableStateFlow<List<HistoryModel>?>(null)
    val historyList: StateFlow<List<HistoryModel>?>
        get() = _historyList

    // Search Book 리스트 Pager
    val searchPager = Pager(PagingConfig(pageSize = 10)) {
        SearchBookPagingSource(searchRepository, _searchKeyword.value)
    }.flow.cachedIn(viewModelScope)

    fun doSearchBooks(query: String?) {
        _searchKeyword.value = query ?: ""
        _isShowHistory.value = false
        insertHistory(HistoryModel(query ?: ""))
    }

    fun getAllHistory() {
        viewModelScope.launch {
            searchRepository.getAllHistory().collectLatest { history ->
                _historyList.value = history.reversed()
            }
        }
    }

    private fun insertHistory(historyModel: HistoryModel) {
        viewModelScope.launch {
            searchRepository.insertHistory(historyModel)
        }
    }

    fun deleteHistory(keyword: String) {
        viewModelScope.launch {
            searchRepository.deleteHistory(keyword)
        }
    }

    fun changeIsShowHistory(isShowHistory: Boolean) {
        _isShowHistory.value = isShowHistory
    }
}