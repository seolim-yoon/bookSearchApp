package com.example.booksearchapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentSubcategoryBinding
import com.example.booksearchapp.ui.viewmodel.CategoryViewModel

class SubCategoryFragment: BaseFragment<FragmentSubcategoryBinding, CategoryViewModel>() {
    override val layoutResID: Int = R.layout.fragment_subcategory
    override val viewModel: CategoryViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(CategoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
    }
}