package com.example.booksearchapp.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        BookListPagingAdapter { model ->
            val bundle = Bundle()
            bundle.putSerializable("BestSellerModel", model)
            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
        initView()
    }

    private fun initView() {
        with(viewDataBinding.rvBookList) {
            adapter = bookAdapter
        }

        // swipe할 때 마다 리스트 새로고침
        with(viewDataBinding.slSwipeRefresh) {
            setOnRefreshListener {
                bookAdapter.refresh()
                isRefreshing = false
            }
        }

        // 카테고리 버튼 누르면 카테고리 선택하는 다이얼로그 뜸
        // TODO : Category/Subcategory 누르면 각각 선택할 수 있는 창 뜨도록
        viewDataBinding.btnSelectCategory.setOnClickListener {
            CategoryBottomSheetFragment().show(requireActivity().supportFragmentManager, "CategoryBottomSheetFragment")
        }

        // 다이얼로그에서 카테고리 선택 후 OK 버튼 누르면 선택한 카테고리의 베스트셀러 가져옴
        viewModel.currentCategoryId.observe(viewLifecycleOwner, Observer {
            bookAdapter.refresh()
        })

        lifecycleScope.launch {
            viewModel.bestSellerPager.collectLatest { pagingData ->
                bookAdapter.submitData(pagingData)
            }
        }
    }
}