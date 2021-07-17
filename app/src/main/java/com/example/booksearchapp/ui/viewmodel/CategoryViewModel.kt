package com.example.booksearchapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.repository.CategoryRepository
import com.example.booksearchapp.util.Category

class CategoryViewModel(application: Application) : BaseViewModel(application) {
    private val categoryRepository = CategoryRepository()

    // 선택된 Category
    var selectCategory: MutableLiveData<String> = MutableLiveData()
    // 선택된 SubCategory
    var selectSubCategory: MutableLiveData<Category> = MutableLiveData()
    // 선택된 Category + SubCategory = 서버에 요청할 카테고리 아이디
    var requestCategoryId: MutableLiveData<String> = MutableLiveData()
    // Category 선택 시 SubCategory에 띄울 리스트
    var subCategoryList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init {
        selectCategory.value = Category.ALL.domestic
        selectSubCategory.value = Category.ALL
        requestCategoryId.value = Category.ALL.domestic
        subCategoryList.value = categoryRepository.domesticList
    }

    fun selectCategory(category: String) {
        selectCategory.value = category
        selectSubCategory.value = Category.ALL
        requestCategoryId.value = category
        subCategoryList.value = when (category) {
            Category.ALL.domestic -> categoryRepository.domesticList
            Category.ALL.foreign -> categoryRepository.foreignList
            Category.ALL.record -> categoryRepository.recordList
            Category.ALL.dvd -> categoryRepository.dvdList
            else -> categoryRepository.domesticList
        }
    }

    fun selectSubCategory(subcategory: Category) {
        selectSubCategory.value = subcategory
        requestCategoryId.value = when (selectCategory.value) {
            Category.ALL.domestic -> subcategory.domestic
            Category.ALL.foreign -> subcategory.foreign
            Category.ALL.record -> subcategory.record
            Category.ALL.dvd -> subcategory.dvd
            else -> subcategory.domestic
        }
    }
}