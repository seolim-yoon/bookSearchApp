package com.example.booksearchapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.booksearchapp.R
import com.example.booksearchapp.databinding.LayoutCategoryBottomSheetBinding
import com.example.booksearchapp.ui.adapter.CategoryFragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class CategoryBottomSheetFragment(context: Context) : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutCategoryBottomSheetBinding
    private val fragmentList = listOf<Fragment>(CategoryFragment(), SubCategoryFragment())
    private val categoryAdapter by lazy {
        CategoryFragmentStateAdapter(this).apply {
            fragments = fragmentList
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_category_bottom_sheet, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        binding.vpCategory.adapter = categoryAdapter
        TabLayoutMediator(binding.tabLayout, binding.vpCategory) {tab, position ->
            when(position) {
                0 -> tab.text = "Category1"
                1 -> tab.text = "Category2"
            }
        }.attach()

    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme
}