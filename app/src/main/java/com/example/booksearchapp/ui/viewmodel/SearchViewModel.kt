package com.example.booksearchapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.data.database.model.HistoryModel
import com.example.booksearchapp.data.paging.SearchBookPagingSource
import com.example.booksearchapp.repository.SearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepositoryImpl: SearchRepositoryImpl) : BaseViewModel() {
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
        SearchBookPagingSource(searchRepositoryImpl, keyword)
    }.flow.cachedIn(viewModelScope)

    init {
        _isShowHistoryLiveData.value = false
    }

    fun doSearchBooks(query: String?) {
        showLoadingAnimation()
        keyword = query ?: ""
        _searchKeywordLiveData.value = query ?: ""
        _isShowHistoryLiveData.value = false
        insertHistory(HistoryModel(query ?: ""))
    }

    fun getAllHistory() {
        addDisposable(
            searchRepositoryImpl.getAllHistory()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { historys ->
                            _historyListLiveData.value = historys.reversed()
                        }
        )
    }

    private fun insertHistory(historyModel: HistoryModel) {
        addDisposable(
            searchRepositoryImpl.insertHistory(historyModel)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {

                        }
        )
    }

    fun deleteHistory(keyword: String) {
        addDisposable(
            searchRepositoryImpl.deleteHistory(keyword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            getAllHistory()
                        }
        )
    }

    fun changeIsShowHistory(isShowHistory: Boolean) {
        _isShowHistoryLiveData.value = isShowHistory
    }
}