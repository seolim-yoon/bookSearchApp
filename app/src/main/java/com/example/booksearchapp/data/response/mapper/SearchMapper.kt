package com.example.booksearchapp.data.response.mapper

import com.example.booksearchapp.data.database.model.SearchModel
import com.example.booksearchapp.data.response.SearchResult
import com.example.booksearchapp.util.Mapper

class SearchMapper : Mapper<SearchResult, List<SearchModel>> {
    override fun map(from: SearchResult): List<SearchModel> =
        from.item.map { book ->
            SearchModel(
                book.itemId,
                book.title,
                book.author,
                book.coverLargeUrl,
                book.priceStandard,
                book.saleStatus,
                book.description
            )
        }
}