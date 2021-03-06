package com.example.booksearchapp.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentSearchBinding
import com.example.booksearchapp.ui.adapter.BookListPagingAdapter
import com.example.booksearchapp.ui.adapter.HistoryAdapter
import com.example.booksearchapp.ui.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val layoutResID: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(SearchViewModel::class.java)
    }

    private val searchBookAdapter by lazy {
        BookListPagingAdapter {
            // TODO : 검색한 도서 아이템 클릭 시 동작 구현 (Detail)
        }
    }

    private val historyAdapter by lazy {
        HistoryAdapter({ selectHistory ->
            // 이전 검색 기록 클릭
            viewModel.isShowHistory.value = false
            viewModel.doSearchBooks(selectHistory.keyword)
        }, { deleteHistory ->
            // 이전 검색 기록 삭제 버튼 클릭
            viewModel.deleteHistory(deleteHistory.keyword)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        with(viewDataBinding.rvBookSearchList) {
            adapter = searchBookAdapter
        }

        with(viewDataBinding.rvBookHistoryList) {
            adapter = historyAdapter
        }

        // swipe할 때 마다 리스트 새로고침
        with(viewDataBinding.slSwipeRefresh) {
            setOnRefreshListener {
                searchBookAdapter.refresh()
                isRefreshing = false
            }
        }

        viewDataBinding.etBookSearchTitle.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                viewModel.isShowHistory.value = true
                viewModel.getAllHistory()
            }
            return@setOnTouchListener false
        }

        viewDataBinding.etBookSearchTitle.setOnEditorActionListener(object  : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.doSearchBooks(viewModel.searchKeyword.value)
                    return true
                }
                return false
            }
        })

        // history insert/delete 후 리스트 변경될 때 마다 history 리스트 갱신
        viewModel.historyList.observe(viewLifecycleOwner, Observer { keywords ->
            historyAdapter.submitList(keywords.orEmpty())
        })

        // 검색 후 Search 버튼 누르면 book 리스트 새로고침
        viewModel.searchKeyword.observe(viewLifecycleOwner, Observer { keyword ->
//            if (keyword.equals("")) {
//                Toast.makeText(context, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
//            }
            searchBookAdapter.refresh()
        })

        lifecycleScope.launch {
            viewModel.searchPager.collectLatest { pagingData ->
                searchBookAdapter.submitData(pagingData)
            }
        }
    }
}