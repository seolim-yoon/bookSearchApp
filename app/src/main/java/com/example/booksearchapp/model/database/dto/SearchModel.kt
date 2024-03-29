package com.example.booksearchapp.model.database.dto

data class SearchModel(
    val itemId: Int,
    val title: String = "",
    val author: String = "",
    val imageUrl: String? = null,
    val priceStandard: Int = 0,
    val saleStatus: String = "",
    val description: String = ""
) : BaseModel