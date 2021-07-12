package com.example.booksearchapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.data.transformBestSellerModel
import com.example.booksearchapp.repository.BookRepository
import io.reactivex.schedulers.Schedulers

class BookViewModel(application: Application): BaseViewModel(application) {
    private val bookRepository = BookRepository(application)

    val pager = Pager(PagingConfig(pageSize = 10)) {
        bookRepository.getAllBestSellers()
    }.flow.cachedIn(viewModelScope)

    fun getBestSellerResult() {
        addDisposable(
                bookRepository.getBestSellerResult()
                        .subscribeOn(Schedulers.io())
                        .subscribe({ modelList ->
                            modelList.transformBestSellerModel().forEach { model ->
                                bookRepository.insertBestSellers(model)
                            }
                        }, { e->
                            Log.e("seolim", "error : " + e.message)

                        })
        )
    }
}