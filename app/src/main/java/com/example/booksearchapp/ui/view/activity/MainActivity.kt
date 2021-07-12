package com.example.booksearchapp.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseActivity
import com.example.booksearchapp.databinding.ActivityMainBinding
import com.example.booksearchapp.ui.viewmodel.BookViewModel

class MainActivity : BaseActivity<ActivityMainBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.activity_main
    override val viewModel: BookViewModel  by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(BookViewModel::class.java)
    }
}