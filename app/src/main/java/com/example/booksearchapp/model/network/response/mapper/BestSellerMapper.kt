package com.example.booksearchapp.model.network.response.mapper

import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.model.network.response.BestSellerResponse
import com.example.booksearchapp.util.Mapper

class BestSellerMapper : Mapper<BestSellerResponse?, List<BestSellerModel>?> {
    override fun map(from: BestSellerResponse?): List<BestSellerModel>? =
        from?.item?.map { book ->
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