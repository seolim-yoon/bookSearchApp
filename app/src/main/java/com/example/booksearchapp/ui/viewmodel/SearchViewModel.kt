package com.example.booksearchapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.data.database.model.HistoryModel
import com.example.booksearchapp.data.paging.SearchBookPagingSource
import com.example.booksearchapp.repository.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel(application: Application) : BaseViewModel(application) {
    private val searchRepository = SearchRepository(application)

    private var keyword = ""

    var searchKeyword: MutableLiveData<String> = MutableLiveData()
    var isShowHistory: MutableLiveData<Boolean> = MutableLiveData()
    var historyList: MutableLiveData<List<HistoryModel>> = MutableLiveData()

    // Search Book 리스트 Pager
    val searchPager = Pager(PagingConfig(pageSize = 10)) {
        SearchBookPagingSource(searchRepository, keyword)
    }.flow.cachedIn(viewModelScope)

    init {
        isShowHistory.value = false
    }

    fun doSearchBooks(query: String?) {
        keyword = query ?: ""
        searchKeyword.value = query ?: ""
        isShowHistory.value = false
        insertHistory(HistoryModel(query ?: ""))
    }

    fun getAllHistory() {
        addDisposable(
                searchRepository.getAllHistory()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { historys ->
                            historyList.value = historys.reversed()
                        }
        )
    }

    private fun insertHistory(historyModel: HistoryModel) {
        addDisposable(
                searchRepository.insertHistory(historyModel)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {

                        }
        )
    }

    fun deleteHistory(keyword: String) {
        addDisposable(
                searchRepository.deleteHistory(keyword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            getAllHistory()
                        }
        )
    }
}