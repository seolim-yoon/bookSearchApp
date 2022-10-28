package com.example.booksearchapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isVisibleLoadingLiveData = MutableLiveData<Boolean>()
    val isVisibleLoadingLiveData: LiveData<Boolean>
        get() = _isVisibleLoadingLiveData


    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    open fun showLoadingAnimation() {
        _isVisibleLoadingLiveData.value = true
    }

    open fun hideLoadingAnimation() {
        _isVisibleLoadingLiveData.value = false
    }
}