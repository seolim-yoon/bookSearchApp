package com.example.booksearchapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.data.database.model.BaseModel
import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.response.mapper.BestSellerMapper
import com.example.booksearchapp.repository.BookRepositoryImpl
import com.example.booksearchapp.util.Category
import com.example.booksearchapp.util.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookRepositoryImpl: BookRepositoryImpl) : BaseViewModel() {

    // 선택된 Category + SubCategory = 서버에 요청할 카테고리 아이디
    private var selectCategoryId = ""

    private val _currentCategoryIdLiveData = MutableLiveData<String>()
    val currentCategoryIdLiveData: LiveData<String>
        get() = _currentCategoryIdLiveData

    private val _currentCategoryNameLiveData = MutableLiveData<String>()
    val currentCategoryNameLiveData: LiveData<String>
        get() = _currentCategoryNameLiveData

    private val _currentSubCategoryNameLiveData = MutableLiveData<String>()
    val currentSubCategoryNameLiveData: LiveData<String>
        get() = _currentSubCategoryNameLiveData

    // 다이얼로그 상태
    private val _dialogStateLiveData = MutableLiveData<StateResult>()
    val dialogStateLiveData: LiveData<StateResult>
        get() = _dialogStateLiveData

    // 선택된 Category
    private val _selectCategoryLiveData = MutableLiveData<String>()
    val selectCategoryLiveData: LiveData<String>
        get() = _selectCategoryLiveData

    // 선택된 SubCategory
    private val _selectSubCategoryLiveData = MutableLiveData<Category>()
    val selectSubCategoryLiveData: LiveData<Category>
        get() = _selectSubCategoryLiveData

    // Category 선택 시 SubCategory에 띄울 리스트
    private val _subCategoryListLiveData = MutableLiveData<ArrayList<String>>()
    val subCategoryListLiveData: LiveData<ArrayList<String>>
        get() = _subCategoryListLiveData

    init {
        selectCategoryId = Category.ALL.domestic
        _selectCategoryLiveData.value = Category.ALL.domestic
        _selectSubCategoryLiveData.value = Category.ALL
        _subCategoryListLiveData.value = bookRepositoryImpl.domesticList
        getBestSellerResult(Category.ALL.domestic)
    }

    // Home 화면 베스트셀러 Pager
    val bestSellerPager = Pager(PagingConfig(pageSize = 10)) {
        // Room DB에서 선택한 카테고리에 해당하는 책 리스트를 가져옴
        bookRepositoryImpl.getAllBestSellersByCategory(_currentCategoryIdLiveData.value ?: Category.ALL.domestic) as PagingSource<Int, BaseModel>
    }.flow.cachedIn(viewModelScope)

    private fun getBestSellerResult(categoryId: String) {
        addDisposable(
            bookRepositoryImpl.getBestSellerResult(categoryId)
                .subscribeOn(Schedulers.io())
                .subscribe({ modelList ->
                    hideLoadingAnimation()
                    insertAllBestSeller(modelList, categoryId)
                }, { e ->
                    Log.e("seolim", "error : " + e.message)
                })
        )
    }

    private fun insertAllBestSeller(modelList: BestSellerResult, categoryId: String) {
        addDisposable(
            bookRepositoryImpl
                .insertAllBestSeller(BestSellerMapper().map(modelList) ?: listOf())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    getBestSellerCategory(categoryId)
                }
        )
    }

    private fun getBestSellerCategory(categoryId: String) {
        addDisposable(
            bookRepositoryImpl
                .getBestSellersCategory(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ name ->
                    Log.i("seolim", "name : " + name)
                    val names = name[0].split(">")
                    if (names.size == 2) {
                        _currentCategoryNameLiveData.value = names[0]
                        _currentSubCategoryNameLiveData.value = names[1]
                    } else {
                        _currentCategoryNameLiveData.value = names[0]
                        _currentSubCategoryNameLiveData.value = "ALL"
                    }

                    _currentCategoryIdLiveData.value = selectCategoryId
                }, { e ->
                    Log.e("seolim", "error db : " + e.message)
                })
        )
    }

    fun onClickOK() {
        showLoadingAnimation()
        getBestSellerResult(selectCategoryId)
        _dialogStateLiveData.value = StateResult.OK
    }

    fun selectCategoryInDialog(category: String) {
        selectCategoryId = category
        _selectCategoryLiveData.value = category
        _selectSubCategoryLiveData.value = Category.ALL
        _subCategoryListLiveData.value = when (category) {
            Category.ALL.domestic -> bookRepositoryImpl.domesticList
            Category.ALL.foreign -> bookRepositoryImpl.foreignList
            Category.ALL.record -> bookRepositoryImpl.recordList
            Category.ALL.dvd -> bookRepositoryImpl.dvdList
            else -> bookRepositoryImpl.domesticList
        }
    }

    fun selectSubCategoryInDialog(subcategory: Category) {
        _selectSubCategoryLiveData.value = subcategory
        selectCategoryId = when (_selectCategoryLiveData.value) {
            Category.ALL.domestic -> subcategory.domestic
            Category.ALL.foreign -> subcategory.foreign
            Category.ALL.record -> subcategory.record
            Category.ALL.dvd -> subcategory.dvd
            else -> subcategory.domestic
        }
    }

    fun changeDialogState(state: StateResult) {
        _dialogStateLiveData.value = state
    }
}