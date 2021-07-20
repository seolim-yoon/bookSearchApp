package com.example.booksearchapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.databinding.FragmentDetailBinding
import com.example.booksearchapp.ui.viewmodel.BookViewModel

class DetailFragment: BaseFragment<FragmentDetailBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.fragment_detail
    override val viewModel: BookViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(BookViewModel::class.java)
    }

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