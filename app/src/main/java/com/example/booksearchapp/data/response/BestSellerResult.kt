package com.example.booksearchapp.data.response

import com.example.booksearchapp.data.database.model.BestSellerModel
import com.google.gson.annotations.SerializedName

data class BestSellerResult(
    @SerializedName("item")
    val item: List<Item>,
    @SerializedName("searchCategoryId")
    val searchCategoryId: String,
    @SerializedName("searchCategoryName")
    val searchCategoryName: String
) {
    data class Item(
        @SerializedName("additionalLink")
        val additionalLink: String,
        @SerializedName("author")
        val author: String,
        @SerializedName("categoryId")
        val categoryId: String,
        @SerializedName("categoryName")
        val categoryName: String,
        @SerializedName("coverLargeUrl")
        val coverLargeUrl: String,
        @SerializedName("coverSmallUrl")
        val coverSmallUrl: String,
        @SerializedName("customerReviewRank")
        val customerReviewRank: Double,
        @SerializedName("description")
        val description: String,
        @SerializedName("discountRate")
        val discountRate: String,
        @SerializedName("isbn")
        val isbn: String,
        @SerializedName("itemId")
        val itemId: Int,
        @SerializedName("link")
        val link: String,
        @SerializedName("mileage")
        val mileage: String,
        @SerializedName("mileageRate")
        val mileageRate: String,
        @SerializedName("mobileLink")
        val mobileLink: String,
        @SerializedName("priceSales")
        val priceSales: Int,
        @SerializedName("priceStandard")
        val priceStandard: Int,
        @SerializedName("pubDate")
        val pubDate: String,
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("rank")
        val rank: Int,
        @SerializedName("reviewCount")
        val reviewCount: Int,
        @SerializedName("saleStatus")
        val saleStatus: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("translator")
        val translator: String
    )
}

fun BestSellerResult.transformBestSellerModel() =
    this.item.map { book ->
        BestSellerModel(book.itemId, book.rank, book.title, book.author, book.coverLargeUrl, book.coverSmallUrl,
                this.searchCategoryId, this.searchCategoryName, book.priceStandard, book.saleStatus, book.description)
    }
