package com.example.booksearchapp.data.response.mapper

import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.util.Mapper

class BestSellerMapper : Mapper<BestSellerResult, List<BestSellerModel>?> {
    override fun map(from: BestSellerResult): List<BestSellerModel>? =
        from.item.map { book ->
            BestSellerModel(
                book.itemId,
                book.rank,
                book.title,
                book.author,
                book.coverLargeUrl,
                book.coverSmallUrl,
                from.searchCategoryId,
                from.searchCategoryName,
                book.priceStandard,
                book.saleStatus,
                book.description
            )
        }
}