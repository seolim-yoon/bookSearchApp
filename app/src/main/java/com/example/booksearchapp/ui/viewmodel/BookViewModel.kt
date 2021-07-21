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
import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.response.transformBestSellerModel
import com.example.booksearchapp.repository.BookRepository
import com.example.booksearchapp.util.Category
import com.example.booksearchapp.util.StateResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookViewModel(application: Application) : BaseViewModel(application) {
    private val bookRepository = BookRepository(application)

    var currentCategoryId: MutableLiveData<String> = MutableLiveData()
    var currentCategoryName: MutableLiveData<String> = MutableLiveData()
    var currentSubCategoryName: MutableLiveData<String> = MutableLiveData()

    // 다이얼로그 상태
    var dialogState: MutableLiveData<StateResult> = MutableLiveData()

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
        dialogState.value = StateResult.OK
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