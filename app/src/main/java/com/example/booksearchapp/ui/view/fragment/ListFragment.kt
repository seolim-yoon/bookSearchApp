package com.example.booksearchapp.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentListBinding
import com.example.booksearchapp.ui.adapter.BookListPagingAdapter
import com.example.booksearchapp.ui.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding, BookViewModel>() {
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
            currentCategoryIdLiveData.observe(viewLifecycleOwner) {
                bookAdapter.refresh()
            }

            isVisibleLoadingLiveData.observe(viewLifecycleOwner) { isVisible ->
                when(isVisible) {
                    true -> viewDataBinding.pbLoading.visibility = View.VISIBLE
                    false -> viewDataBinding.pbLoading.visibility = View.GONE
                }
            }

            selectModelLiveData.observe(viewLifecycleOwner) { model ->
                findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundleOf("BestSellerModel" to model))
            }

            lifecycleScope.launch {
                bestSellerPager.collectLatest { pagingData ->
                    bookAdapter.submitData(pagingData)
                }
            }
        }
    }
}