package com.example.booksearchapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.data.database.model.BaseModel
import com.example.booksearchapp.data.paging.SearchBookPagingSource
import com.example.booksearchapp.data.response.transformBestSellerModel
import com.example.booksearchapp.repository.BookRepository
import io.reactivex.schedulers.Schedulers

class BookViewModel(application: Application): BaseViewModel(application) {
    private val bookRepository = BookRepository(application)
    private var keyword = ""

    var searchKeyword: MutableLiveData<String> = MutableLiveData()

    val bestSellerPager = Pager(PagingConfig(pageSize = 10)) {
        bookRepository.getAllBestSellers() as PagingSource<Int, BaseModel>
    }.flow.cachedIn(viewModelScope)

    val searchPager = Pager(PagingConfig(pageSize = 10)) {
        SearchBookPagingSource(bookRepository, keyword ?: "")
    }.flow.cachedIn(viewModelScope)

    fun getSearchBooks(query: String) {
        searchKeyword.value = query
        keyword = query
    }

    fun getBestSellerResult() {
        addDisposable(
                bookRepository.getBestSellerResult()
                        .subscribeOn(Schedulers.io())
                        .subscribe({ modelList ->
                            bookRepository.insertAllBestSeller(modelList.transformBestSellerModel())
                        }, { e->
                            Log.e("seolim", "error : " + e.message)

                        })
        )
    }
}