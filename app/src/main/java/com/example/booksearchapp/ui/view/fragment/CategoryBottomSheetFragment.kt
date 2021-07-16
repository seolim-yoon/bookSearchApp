package com.example.booksearchapp.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.booksearchapp.R
import com.example.booksearchapp.databinding.LayoutCategoryBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheetFragment(context: Context) : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutCategoryBottomSheetBinding
    private val mContext: Context = context
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_category_bottom_sheet, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

    }

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme
}