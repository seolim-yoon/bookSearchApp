package com.example.booksearchapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentSearchBinding
import com.example.booksearchapp.ui.adapter.BookListPagingAdapter
import com.example.booksearchapp.ui.viewmodel.BookViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.fragment_search
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
        viewDataBinding.viewmodel = viewModel
        initView()
    }

    private fun initView() {
        with(viewDataBinding.rvBookSearchList) {
            adapter = bookAdapter
        }

        // 검색 후 Search 버튼 누르면 리스트 새로고침
        viewModel.searchKeyword.observe(viewLifecycleOwner, Observer { keyword ->
            if (keyword.equals("")) {
                Toast.makeText(context, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            bookAdapter.refresh()
        })

        lifecycleScope.launch {
            viewModel.searchPager.collectLatest { pagingData ->
                bookAdapter.submitData(pagingData)
            }
        }
    }
}