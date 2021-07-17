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
import com.example.booksearchapp.util.Category
import io.reactivex.schedulers.Schedulers

class BookViewModel(application: Application) : BaseViewModel(application) {
    private val bookRepository = BookRepository(application)
    private var keyword = ""

    var searchKeyword: MutableLiveData<String> = MutableLiveData()
    var selectCategoryId: MutableLiveData<String> = MutableLiveData()

    // Home 화면 베스트셀러 Pager
    val bestSellerPager = Pager(PagingConfig(pageSize = 10)) {
        // Room DB에서 선택한 카테고리에 해당하는 책 리스트를 가져옴
        bookRepository.getAllBestSellersByCategory(selectCategoryId.value ?: Category.ALL.domestic) as PagingSource<Int, BaseModel>
    }.flow.cachedIn(viewModelScope)

    // Search 화면 Pager
    val searchPager = Pager(PagingConfig(pageSize = 10)) {
        SearchBookPagingSource(bookRepository, keyword)
    }.flow.cachedIn(viewModelScope)

    fun doSearchBooks(query: String?) {
        keyword = query ?: ""
        searchKeyword.value = query ?: ""
    }

    // 선택한 카테고리의 책 결과를 서버에서 받아와서 Room DB에 넣음
    fun getBestSellerResult(categoryId: String) {
        addDisposable(
                bookRepository.getBestSellerResult(categoryId)
                        .subscribeOn(Schedulers.io())
                        .subscribe({ modelList ->
                            bookRepository.insertAllBestSeller(modelList.transformBestSellerModel())
                        }, { e ->
                            Log.e("seolim", "error : " + e.message)
                        })
        )
    }

    fun selectOK() {
//        this.selectCategoryId.value = selectCategoryId
    }
}