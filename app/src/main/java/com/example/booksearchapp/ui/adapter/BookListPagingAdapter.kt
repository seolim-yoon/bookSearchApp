package com.example.booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.R
import com.example.booksearchapp.databinding.ItemBookListBinding
import com.example.booksearchapp.ui.model.BaseModel
import com.example.booksearchapp.ui.model.BestSellerModel

class BookListPagingAdapter(
    private var bookItemClick: (BestSellerModel) -> Unit
) : PagingDataAdapter<BestSellerModel, BookListPagingAdapter.PagingItemViewHolder>(diffCallback){
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<BestSellerModel>() {
            override fun areItemsTheSame(oldItem: BestSellerModel, newItem: BestSellerModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BestSellerModel, newItem: BestSellerModel): Boolean {
                return if(oldItem is BestSellerModel && newItem is BestSellerModel) {
                    oldItem.itemId == newItem.itemId
                } else{
                    oldItem == newItem
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListPagingAdapter.PagingItemViewHolder = PagingItemViewHolder(parent)

    override fun onBindViewHolder(holder: BookListPagingAdapter.PagingItemViewHolder, position: Int) {
        when(holder) {
            is PagingItemViewHolder -> holder.bind(getItem(position) as BestSellerModel)
        }
    }


    inner class PagingItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_book_list, parent, false)
    ) {
        private val binding = ItemBookListBinding.bind(itemView)

        fun bind(bestseller: BestSellerModel) {
            binding.bestseller = bestseller
            binding.executePendingBindings()

            itemView.setOnClickListener {
                bookItemClick(bestseller)
            }
        }
    }
}