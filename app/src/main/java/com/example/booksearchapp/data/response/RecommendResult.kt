package com.example.booksearchapp.data.response


import com.example.booksearchapp.data.database.model.RecommendModel
import com.google.gson.annotations.SerializedName

data class RecommendResult(
    @SerializedName("item")
    val item: List<Item>
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
