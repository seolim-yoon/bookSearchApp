package com.example.booksearchapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.booksearchapp.base.BaseViewModel
import com.example.booksearchapp.repository.CategoryRepository
import com.example.booksearchapp.util.Category

class CategoryViewModel(application: Application) : BaseViewModel(application) {
    private val categoryRepository = CategoryRepository()

    var selectCategory: MutableLiveData<Int> = MutableLiveData()
    var subCategoryList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    init {
        selectCategory.value = Category.DOMESTIC.num
        subCategoryList.value = categoryRepository.domesticList
    }

    fun selectCategory(category: Int) {
        selectCategory.value = category
        when(category) {
            Category.DOMESTIC.num -> subCategoryList.value = categoryRepository.domesticList
            Category.FOREIGN.num -> subCategoryList.value = categoryRepository.foreignList
            Category.RECORD.num -> subCategoryList.value = categoryRepository.recordList
            Category.DVD.num -> subCategoryList.value = categoryRepository.dvdList
        }
    }
}