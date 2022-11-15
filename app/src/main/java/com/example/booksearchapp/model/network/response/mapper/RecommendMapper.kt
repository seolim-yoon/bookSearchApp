package com.example.booksearchapp.model.network.response.mapper

import com.example.booksearchapp.model.database.dto.RecommendModel
import com.example.booksearchapp.model.network.response.RecommendResponse
import com.example.booksearchapp.util.Mapper

class RecommendMapper : Mapper<RecommendResponse, List<RecommendModel>?> {
    override fun map(from: RecommendResponse): List<RecommendModel>? =
        from.item.map { book ->
            RecommendModel(book.itemId, book.title, book.author, book.coverSmallUrl)
        }
}