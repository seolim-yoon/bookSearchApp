package com.example.booksearchapp.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel: ViewModel() {

    private val _isVisibleLoading = MutableStateFlow(false)
    val isVisibleLoading: StateFlow<Boolean>
        get() = _isVisibleLoading

    open fun showLoadingAnimation() {
        _isVisibleLoading.value = true
    }

    open fun hideLoadingAnimation() {
        _isVisibleLoading.value = false
    }
}