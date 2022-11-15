package com.example.booksearchapp.ui.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.data.database.model.SearchModel
import com.example.booksearchapp.databinding.ItemBookSearchListBinding

class SearchPagingItemViewHolder(
    private val binding: ItemBookSearchListBinding,
    private var bookItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        itemView.setOnClickListener {
            bookItemClick(absoluteAdapterPosition)
        }
    }

    fun bind(search: SearchModel) {
        binding.search = search
    }
}