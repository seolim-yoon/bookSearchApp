package com.example.booksearchapp.feature.book

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentListBinding
import com.example.booksearchapp.feature.category.CategoryBottomSheetFragment
import com.example.booksearchapp.ui.adapter.BookListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : BaseFragment<FragmentListBinding, BookViewModel>() {
    override val layoutResID: Int = R.layout.fragment_list
    override val viewModel: BookViewModel by activityViewModels()

    private val bookAdapter by lazy {
        BookListPagingAdapter { position ->
            viewModel.getSelectBestSeller(position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
        initView()
        initObserver()
    }

    private fun initView() {
        with(viewDataBinding) {
            rvBookList.adapter = bookAdapter

            slSwipeRefresh.setOnRefreshListener {
                bookAdapter.refresh()
                slSwipeRefresh.isRefreshing = false
            }

            // 카테고리 버튼 누르면 카테고리 선택하는 다이얼로그 뜸
            btnSelectCategory.setOnClickListener {
                CategoryBottomSheetFragment().show(requireActivity().supportFragmentManager, "CategoryBottomSheetFragment")
            }
        }
    }

    private fun initObserver() {
        with(viewModel) {
            // 다이얼로그에서 카테고리 선택 후 OK 버튼 누르면 선택한 카테고리의 베스트셀러 가져옴
            lifecycleScope.launch {
                dialogState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect {
                        bookAdapter.refresh()
                    }
            }

            lifecycleScope.launch {
                isVisibleLoading.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { isVisible ->
                        when(isVisible) {
                            true -> viewDataBinding.pbLoading.visibility = View.VISIBLE
                            false -> viewDataBinding.pbLoading.visibility = View.GONE
                        }
                    }
            }

            lifecycleScope.launch {
                selectModel.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { model ->
                        model?.let {
                            findNavController().navigate(
                                R.id.action_listFragment_to_detailFragment,
                                bundleOf("BestSellerModel" to model)
                            )
                        }
                    }
            }

            lifecycleScope.launch {
                bestSellerPager.collectLatest { pagingData ->
                    bookAdapter.submitData(pagingData)
                }
            }
        }
    }
}