package com.example.booksearchapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.booksearchapp.R
import com.example.booksearchapp.base.BaseFragment
import com.example.booksearchapp.databinding.FragmentSearchBinding
import com.example.booksearchapp.ui.adapter.BookListPagingAdapter
import com.example.booksearchapp.ui.adapter.HistoryAdapter
import com.example.booksearchapp.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val layoutResID: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by activityViewModels()

    private val searchBookAdapter by lazy {
        BookListPagingAdapter { model ->
//            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundleOf("SearchModel" to model))
        }
    }

    private val historyAdapter by lazy {
        HistoryAdapter({ selectHistory ->
            // 이전 검색 기록 클릭
            viewModel.changeIsShowHistory(false)
            viewModel.doSearchBooks(selectHistory.keyword)
            hideKeyboard()
        }, { deleteHistory ->
            // 이전 검색 기록 삭제 버튼 클릭
            viewModel.deleteHistory(deleteHistory.keyword)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewmodel = viewModel
        initView()
        initObserver()
    }

    private fun initView() {
        with(viewDataBinding) {
            rvBookSearchList.adapter = searchBookAdapter
            rvBookHistoryList.adapter = historyAdapter
            slSwipeRefresh.setOnRefreshListener {
                searchBookAdapter.refresh()
                slSwipeRefresh.isRefreshing = false
            }

            etBookSearchTitle.setOnTouchListener { v, event ->
                if(event.action == MotionEvent.ACTION_DOWN) {
                    viewModel.changeIsShowHistory(true)
                    viewModel.getAllHistory()
                }
                return@setOnTouchListener false
            }

            etBookSearchTitle.setOnEditorActionListener(object  : TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if(actionId == EditorInfo.IME_ACTION_DONE) {
                        viewModel.doSearchBooks(v?.text.toString())
                        hideKeyboard()
                        return true
                    }
                    return false
                }
            })
        }
    }

    private fun initObserver() {
        with(viewModel) {
            // history insert/delete 후 리스트 변경될 때 마다 history 리스트 갱신
            historyListLiveData.observe(viewLifecycleOwner) { keywords ->
                historyAdapter.submitList(keywords.orEmpty())
            }

            // 검색 후 Search 버튼 누르면 book 리스트 새로고침
            searchKeywordLiveData.observe(viewLifecycleOwner) { keyword ->
                searchBookAdapter.refresh()
            }

            lifecycleScope.launch {
                searchPager.collectLatest { pagingData ->
                    searchBookAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewDataBinding.etBookSearchTitle.windowToken, 0)
    }
}