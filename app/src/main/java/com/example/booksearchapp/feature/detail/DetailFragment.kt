package com.example.booksearchapp.feature.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.databinding.FragmentDetailBinding
import com.example.booksearchapp.feature.book.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: BaseFragment<FragmentDetailBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.fragment_detail
    override val viewModel: BookViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewDataBinding.model = arguments?.getSerializable("BestSellerModel") as BestSellerModel

        viewDataBinding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}