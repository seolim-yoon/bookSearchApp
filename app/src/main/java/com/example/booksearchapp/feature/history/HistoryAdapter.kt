package com.example.booksearchapp.feature.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.booksearchapp.model.database.dto.HistoryModel
import com.example.booksearchapp.databinding.ItemHistoryBinding

class HistoryAdapter(
    private var historyItemClick: (HistoryModel) -> Unit,
    private var historyItemDelete: (HistoryModel) -> Unit
) : ListAdapter<HistoryModel, HistoryAdapter.HistoryViewHolder>(diffUtil) {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(historyModel: HistoryModel) {
            binding.history = historyModel

            itemView.setOnClickListener {
                historyItemClick(historyModel)
            }
            binding.ivDeleteHistory.setOnClickListener {
                historyItemDelete(historyModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder
            = HistoryViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<HistoryModel>() {
            override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel) =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel) =
                    oldItem.keyword == newItem.keyword
        }
    }
}