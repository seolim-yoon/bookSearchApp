package com.example.booksearchapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.data.database.model.BaseModel
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.database.model.SearchModel
import com.example.booksearchapp.databinding.ItemBookListBinding
import com.example.booksearchapp.databinding.ItemBookSearchListBinding

class BookListPagingAdapter(
    private var bookItemClick: (BaseModel) -> Unit
) : PagingDataAdapter<BaseModel, RecyclerView.ViewHolder>(diffCallback){
    private val VIEW_TYPE_BEST_SELLER = 0
    private val VIEW_TYPE_SEARCH = 1

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<BaseModel>() {
            override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
                return if(oldItem is BestSellerModel && newItem is BestSellerModel) {
                    oldItem.itemId == newItem.itemId
                } else if(oldItem is SearchModel && newItem is SearchModel) {
                    oldItem.itemId == newItem.itemId
                } else{
                    oldItem == newItem
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when(viewType) {
                VIEW_TYPE_BEST_SELLER -> {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemBookListBinding.inflate(layoutInflater, parent, false)
                    BestSellerPagingItemViewHolder(binding)
                }
                VIEW_TYPE_SEARCH -> {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemBookSearchListBinding.inflate(layoutInflater, parent, false)
                    SearchPagingItemViewHolder(binding)
                }
                else -> {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = ItemBookListBinding.inflate(layoutInflater, parent, false)
                    BestSellerPagingItemViewHolder(binding)
                }
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { model ->
            when(holder) {
                is BestSellerPagingItemViewHolder -> holder.bind(model as BestSellerModel)
                is SearchPagingItemViewHolder -> holder.bind(model as SearchModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BestSellerModel -> VIEW_TYPE_BEST_SELLER
            is SearchModel -> VIEW_TYPE_SEARCH
            else -> VIEW_TYPE_BEST_SELLER
        }
    }

    inner class BestSellerPagingItemViewHolder(private val binding: ItemBookListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(bestseller: BestSellerModel) {
            binding.bestseller = bestseller
            binding.executePendingBindings()

            itemView.setOnClickListener {
                bookItemClick(bestseller)
            }
        }
    }

    inner class SearchPagingItemViewHolder(private val binding: ItemBookSearchListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(search: SearchModel) {
            binding.search = search
            binding.executePendingBindings()

            itemView.setOnClickListener {
                bookItemClick(search)
            }
        }
    }
}