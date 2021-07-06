package com.example.booksearchapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T: ViewDataBinding, R: BaseViewModel> : AppCompatActivity() {
    lateinit var viewDataBinding: T

    abstract val layoutResID: Int
    abstract val viewModel: R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResID)
        viewDataBinding.lifecycleOwner = this@BaseActivity
    }
}