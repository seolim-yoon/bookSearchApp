package com.example.booksearchapp.model.network.response.mapper

import com.example.booksearchapp.model.database.dto.SearchModel
import com.example.booksearchapp.model.network.response.SearchResponse
import com.example.booksearchapp.util.Mapper

class SearchMapper : Mapper<SearchResponse?, List<SearchModel>?> {
    override fun map(from: SearchResponse?): List<SearchModel>? =
        from?.item?.map { book ->
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