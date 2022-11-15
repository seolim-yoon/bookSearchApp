package com.example.booksearchapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {

    private val _isVisibleLoadingLiveData = MutableLiveData<Boolean>()
    val isVisibleLoadingLiveData: LiveData<Boolean>
        get() = _isVisibleLoadingLiveData

    open fun showLoadingAnimation() {
        _isVisibleLoadingLiveData.postValue(true)
    }

    open fun hideLoadingAnimation() {
        _isVisibleLoadingLiveData.postValue(false)
    }
}