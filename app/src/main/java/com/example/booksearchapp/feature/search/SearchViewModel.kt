package com.example.booksearchapp.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.model.database.dto.HistoryModel
import com.example.booksearchapp.data.paging.SearchBookPagingSource
import com.example.booksearchapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : BaseViewModel() {
    private var keyword = ""

    private val _searchKeywordLiveData = MutableLiveData<String>()
    val searchKeywordLiveData: LiveData<String>
        get() = _searchKeywordLiveData

    private val _isShowHistoryLiveData = MutableLiveData<Boolean>()
    val isShowHistoryLiveData: LiveData<Boolean>
        get() = _isShowHistoryLiveData

    private val _historyListLiveData = MutableLiveData<List<HistoryModel>>()
    val historyListLiveData: LiveData<List<HistoryModel>>
        get() = _historyListLiveData

    // Search Book 리스트 Pager
    val searchPager = Pager(PagingConfig(pageSize = 10)) {
        SearchBookPagingSource(searchRepository, keyword)
    }.flow.cachedIn(viewModelScope)

    init {
        _isShowHistoryLiveData.value = false
    }

    fun doSearchBooks(query: String?) {
        keyword = query ?: ""
        _searchKeywordLiveData.value = query ?: ""
        _isShowHistoryLiveData.value = false
        insertHistory(HistoryModel(query ?: ""))
    }

    fun getAllHistory() {
        viewModelScope.launch {
            searchRepository.getAllHistory().collectLatest { history ->
                _historyListLiveData.value = history.reversed()
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
        _isShowHistoryLiveData.value = isShowHistory
    }
}