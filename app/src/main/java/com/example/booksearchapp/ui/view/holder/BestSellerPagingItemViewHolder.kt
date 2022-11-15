package com.example.booksearchapp.ui.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.databinding.ItemBookListBinding

class BestSellerPagingItemViewHolder(
    private val binding: ItemBookListBinding,
    private var bookItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            bookItemClick(absoluteAdapterPosition)
        }
    }

    fun bind(bestseller: BestSellerModel) {
        binding.bestseller = bestseller
    }
}