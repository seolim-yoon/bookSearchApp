package com.example.booksearchapp.model.database.dto

data class RecommendModel(
    val itemId: Int,
    val title: String = "",
    val author: String = "",
    val imageUrl: String? = null
): BaseModel