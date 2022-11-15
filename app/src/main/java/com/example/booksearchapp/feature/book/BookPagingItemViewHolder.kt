package com.example.booksearchapp.feature.book

import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.databinding.ItemBookListBinding

class BookPagingItemViewHolder(
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