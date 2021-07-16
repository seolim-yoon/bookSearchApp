package com.example.booksearchapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentListBinding
import com.example.booksearchapp.ui.adapter.BookListPagingAdapter
import com.example.booksearchapp.ui.viewmodel.BookViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListFragment : BaseFragment<FragmentListBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.fragment_list
    override val viewModel: BookViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(BookViewModel::class.java)
    }

    private val bookAdapter by lazy {
        BookListPagingAdapter {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(viewDataBinding.rvBookList) {
            adapter = bookAdapter
        }

        with(viewDataBinding.slSwipeRefresh) {
            setOnRefreshListener {
                bookAdapter.refresh()
                isRefreshing = false
            }
        }

        viewDataBinding.btnSelectCategory.setOnClickListener {
            CategoryBottomSheetFragment(requireContext().applicationContext).show(requireActivity().supportFragmentManager, "CategoryBottomSheetFragment")
        }

        viewModel.getBestSellerResult()
        lifecycleScope.launch {
            viewModel.bestSellerPager.collectLatest { pagingData ->
                bookAdapter.submitData(pagingData)
            }
        }
    }
}