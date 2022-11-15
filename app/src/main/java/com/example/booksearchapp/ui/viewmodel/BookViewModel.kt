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
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.mapper.BestSellerMapper
import com.example.booksearchapp.repository.BookRepository
import com.example.booksearchapp.util.Category
import com.example.booksearchapp.util.SingleLiveEvent
import com.example.booksearchapp.util.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookRepository: BookRepository) :
    BaseViewModel() {

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

    // 리스트 아이템 클릭 시 화면 전환에 필요한 BestSellerModel
    private val _selectModelLiveData = SingleLiveEvent<BestSellerModel>()
    val selectModelLiveData: SingleLiveEvent<BestSellerModel>
        get() = _selectModelLiveData

    val bestSellerPager = Pager(PagingConfig(pageSize = 10)) {
        bookRepository.getAllBestSellersByCategory(
            _currentCategoryIdLiveData.value ?: Category.ALL.domestic
        ) as PagingSource<Int, BaseModel>
    }.flow.cachedIn(viewModelScope)

    init {
        selectCategoryId = Category.ALL.domestic
        _selectCategoryLiveData.value = Category.ALL.domestic
        _selectSubCategoryLiveData.value = Category.ALL
        _subCategoryListLiveData.value = bookRepository.domesticList
        getBestSellerResult(Category.ALL.domestic)
    }

    private fun getBestSellerResult(categoryId: String) {
        viewModelScope.launch {
            bookRepository.getBestSellerResult(categoryId).collectLatest { bookResult ->
                hideLoadingAnimation()
                insertAllBestSeller(BestSellerMapper().map(bookResult) ?: listOf(), categoryId)
            }
        }
    }

    private fun getBestSellerCategory(categoryId: String) {
        viewModelScope.launch {
            bookRepository.getBestSellersCategory(categoryId).collectLatest { bookResult ->
                val name = bookResult
                val names = name[0].split(">")
                if (names.size == 2) {
                    _currentCategoryNameLiveData.value = names[0]
                    _currentSubCategoryNameLiveData.value = names[1]
                } else {
                    _currentCategoryNameLiveData.value = names[0]
                    _currentSubCategoryNameLiveData.value = "ALL"
                }

                _currentCategoryIdLiveData.value = selectCategoryId
            }
        }
    }

    private fun insertAllBestSeller(modelList: List<BestSellerModel>, categoryId: String) {
        viewModelScope.launch {
            bookRepository.insertAllBestSeller(modelList).collectLatest {
                getBestSellerCategory(categoryId)
            }
        }
    }

    fun getSelectBestSeller(position: Int) {
        viewModelScope.launch {
            bookRepository.getSelectBestSeller(position).collectLatest { bestSellerModel ->
                _selectModelLiveData.value = bestSellerModel
            }
        }
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
            Category.ALL.domestic -> bookRepository.domesticList
            Category.ALL.foreign -> bookRepository.foreignList
            Category.ALL.record -> bookRepository.recordList
            Category.ALL.dvd -> bookRepository.dvdList
            else -> bookRepository.domesticList
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