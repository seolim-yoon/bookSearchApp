package com.example.booksearchapp.feature.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentCategoryBinding
import com.example.booksearchapp.feature.book.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment: BaseFragment<FragmentCategoryBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.fragment_category
    override val viewModel: BookViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
    }
}