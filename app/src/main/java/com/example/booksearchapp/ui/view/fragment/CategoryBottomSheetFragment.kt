package com.example.booksearchapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.booksearchapp.R
import com.example.booksearchapp.databinding.LayoutCategoryBottomSheetBinding
import com.example.booksearchapp.ui.adapter.CategoryFragmentStateAdapter
import com.example.booksearchapp.ui.viewmodel.BookViewModel
import com.example.booksearchapp.util.StateResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class CategoryBottomSheetFragment() : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutCategoryBottomSheetBinding
    private val viewModel: BookViewModel by lazy {
        ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(BookViewModel::class.java)
    }

    // bottom sheet dialog에서 사용할 Fragment 리스트 (CategoryFragment/SubCategoryFragment)
    private val fragmentList = listOf<Fragment>(CategoryFragment(), SubCategoryFragment())
    private val categoryAdapter by lazy {
        CategoryFragmentStateAdapter(this).apply {
            fragments = fragmentList
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_category_bottom_sheet, container, false)
        binding.viewmodel = viewModel
        initView()
        return binding.root
    }

    private fun initView() {
        binding.vpCategory.adapter = categoryAdapter

        // tablayout이랑 viewpager 연결
        TabLayoutMediator(binding.tabLayout, binding.vpCategory) {tab, position ->
            when(position) {
                0 -> tab.text = "Category"
                1 -> tab.text = "Type"
            }
        }.attach()

        // OK 버튼 누르면 다이얼로그 dismiss
        viewModel.dialogStateLiveData.observe(viewLifecycleOwner) { result ->
            if(result == StateResult.OK) {
                viewModel.changeDialogState(StateResult.NONE)
                dismiss()
            }
        }
    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme
}