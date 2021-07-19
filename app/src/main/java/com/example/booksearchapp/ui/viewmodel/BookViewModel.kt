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
import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.response.transformBestSellerModel
import com.example.booksearchapp.repository.BookRepository
import com.example.booksearchapp.util.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookViewModel(application: Application) : BaseViewModel(application) {
    private val bookRepository = BookRepository(application)
    private var keyword = ""

    var searchKeyword: MutableLiveData<String> = MutableLiveData()
    var currentCategoryId: MutableLiveData<String> = MutableLiveData()
    var currentCategoryName: MutableLiveData<String> = MutableLiveData()
    var currentSubCategoryName: MutableLiveData<String> = MutableLiveData()

    // 선택된 Category
    var selectCategory: MutableLiveData<String> = MutableLiveData()
    // 선택된 SubCategory
    var selectSubCategory: MutableLiveData<Category> = MutableLiveData()
    // 선택된 Category + SubCategory = 서버에 요청할 카테고리 아이디
    private var selectCategoryId: MutableLiveData<String> = MutableLiveData()
    // Category 선택 시 SubCategory에 띄울 리스트
    var subCategoryList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init {
        selectCategory.value = Category.ALL.domestic
        selectSubCategory.value = Category.ALL
        selectCategoryId.value = Category.ALL.domestic
        subCategoryList.value = bookRepository.domesticList
        getBestSellerResult(Category.ALL.domestic)
    }

    // Home 화면 베스트셀러 Pager
    val bestSellerPager = Pager(PagingConfig(pageSize = 10)) {
        // Room DB에서 선택한 카테고리에 해당하는 책 리스트를 가져옴
        bookRepository.getAllBestSellersByCategory(currentCategoryId.value ?: Category.ALL.domestic) as PagingSource<Int, BaseModel>
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
    private fun getBestSellerResult(categoryId: String) {
        addDisposable(
                bookRepository.getBestSellerResult(categoryId)
                        .subscribeOn(Schedulers.io())
                        .subscribe({ modelList ->
                            insertAllBestSeller(modelList, categoryId)
                        }, { e ->
                            Log.e("seolim", "error : " + e.message)
                        })
        )
    }

    // Room DB에 넣은 후 CategoryName을 가져옴
    private fun insertAllBestSeller(modelList: BestSellerResult, categoryId: String) {
        addDisposable(
                bookRepository.insertAllBestSeller(modelList.transformBestSellerModel())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            getBestSellerCategory(categoryId)
                        }
        )
    }

    // 가져온 CategoryName을 View에 띄움
    private fun getBestSellerCategory(categoryId: String) {
        addDisposable(
                bookRepository.getBestSellersCategory(categoryId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe ({ name ->
                            val names = name[0].split(">")
                            if(names.size == 2) {
                                currentCategoryName.value = names[0]
                                currentSubCategoryName.value = names[1]
                            } else {
                                currentCategoryName.value = names[0]
                                currentSubCategoryName.value = "ALL"
                            }
                        }, { e ->
                            Log.e("seolim", "error db : " + e.message)

                        })
        )
    }

    fun onClickOK() {
        currentCategoryId.value = selectCategoryId.value
        getBestSellerResult(selectCategoryId.value ?: Category.ALL.domestic)
    }

    fun selectCategoryInDialog(category: String) {
        selectCategory.value = category
        selectSubCategory.value = Category.ALL
        selectCategoryId.value = category
        subCategoryList.value = when (category) {
            Category.ALL.domestic -> bookRepository.domesticList
            Category.ALL.foreign -> bookRepository.foreignList
            Category.ALL.record -> bookRepository.recordList
            Category.ALL.dvd -> bookRepository.dvdList
            else -> bookRepository.domesticList
        }
    }

    fun selectSubCategoryInDialog(subcategory: Category) {
        selectSubCategory.value = subcategory
        selectCategoryId.value = when (selectCategory.value) {
            Category.ALL.domestic -> subcategory.domestic
            Category.ALL.foreign -> subcategory.foreign
            Category.ALL.record -> subcategory.record
            Category.ALL.dvd -> subcategory.dvd
            else -> subcategory.domestic
        }
    }
}