package com.example.booksearchapp.data.response.mapper

import com.example.booksearchapp.data.database.model.RecommendModel
import com.example.booksearchapp.data.response.RecommendResult
import com.example.booksearchapp.util.Mapper

class RecommendMapper : Mapper<RecommendResult, List<RecommendModel>?> {
    override fun map(from: RecommendResult): List<RecommendModel>? =
        from.item.map { book ->
            RecommendModel(book.itemId, book.title, book.author, book.coverSmallUrl)
        }
}