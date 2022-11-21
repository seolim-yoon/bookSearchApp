package com.example.booksearchapp.feature.book

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.model.database.dto.BaseModel
import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.model.network.response.mapper.BestSellerMapper
import com.example.booksearchapp.repository.BookRepository
import com.example.booksearchapp.util.Category
import com.example.booksearchapp.util.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookRepository: BookRepository) :
    BaseViewModel() {

    // 선택된 Category + SubCategory = 서버에 요청할 카테고리 아이디
    private var selectCategoryId = ""

    private val _currentCategoryName = MutableStateFlow("국내도서")
    val currentCategoryName: StateFlow<String>
        get() = _currentCategoryName

    private val _currentSubCategoryName = MutableStateFlow("ALL")
    val currentSubCategoryName: StateFlow<String>
        get() = _currentSubCategoryName

    // 다이얼로그 상태
    private val _dialogState = MutableStateFlow(StateResult.NONE)
    val dialogState: StateFlow<StateResult>
        get() = _dialogState

    // 선택된 Category
    private val _selectCategory = MutableStateFlow(Category.ALL.domestic)
    val selectCategory: StateFlow<String>
        get() = _selectCategory

    // 선택된 SubCategory
    private val _selectSubCategory = MutableStateFlow(Category.ALL)
    val selectSubCategory: StateFlow<Category>
        get() = _selectSubCategory

    // Category 선택 시 SubCategory에 띄울 리스트
    private val _subCategoryList = MutableStateFlow(bookRepository.domesticList)
    val subCategoryList: StateFlow<ArrayList<String>>
        get() = _subCategoryList

    // 리스트 아이템 클릭 시 화면 전환에 필요한 BestSellerModel
    private val _selectModel = MutableStateFlow<BestSellerModel?>(null)
    val selectModel: StateFlow<BestSellerModel?>
        get() = _selectModel

    val bestSellerPager = Pager(PagingConfig(pageSize = 10)) {
        bookRepository.getAllBestSellersByCategory(selectCategoryId) as PagingSource<Int, BaseModel>
    }.flow.cachedIn(viewModelScope)

    init {
        selectCategoryId = Category.ALL.domestic
        getBestSellerResult(Category.ALL.domestic)
    }

    private fun getBestSellerResult(categoryId: String) {
        viewModelScope.launch {
            bookRepository.getBestSellerResult(categoryId).collectLatest { bookResult ->
                hideLoadingAnimation()
                insertAllBestSeller(BestSellerMapper().map(bookResult) ?: listOf(), categoryId)

                val names = bookResult.searchCategoryName.split(">")
                if (names.size == 2) {
                    _currentCategoryName.value = names[0]
                    _currentSubCategoryName.value = names[1]
                } else {
                    _currentCategoryName.value = names[0]
                    _currentSubCategoryName.value = "ALL"
                }
            }
        }
    }

    private fun insertAllBestSeller(modelList: List<BestSellerModel>, categoryId: String) {
        viewModelScope.launch {
            bookRepository.insertAllBestSeller(modelList)
        }
    }

    fun getSelectBestSeller(position: Int) {
        viewModelScope.launch {
            bookRepository.getSelectBestSeller(position).collectLatest { bestSellerModel ->
                _selectModel.value = bestSellerModel
            }
        }
    }

    fun onClickOK() {
        showLoadingAnimation()
        getBestSellerResult(selectCategoryId)
        _dialogState.value = StateResult.OK
    }

    fun selectCategoryInDialog(category: String) {
        selectCategoryId = category
        _selectCategory.value = category
        _selectSubCategory.value = Category.ALL
        _subCategoryList.value = when (category) {
            Category.ALL.domestic -> bookRepository.domesticList
            Category.ALL.foreign -> bookRepository.foreignList
            Category.ALL.record -> bookRepository.recordList
            Category.ALL.dvd -> bookRepository.dvdList
            else -> bookRepository.domesticList
        }
    }

    fun selectSubCategoryInDialog(subcategory: Category) {
        _selectSubCategory.value = subcategory
        selectCategoryId = when (_selectCategory.value) {
            Category.ALL.domestic -> subcategory.domestic
            Category.ALL.foreign -> subcategory.foreign
            Category.ALL.record -> subcategory.record
            Category.ALL.dvd -> subcategory.dvd
            else -> subcategory.domestic
        }
    }

    fun changeDialogState(state: StateResult) {
        _dialogState.value = state
    }
}