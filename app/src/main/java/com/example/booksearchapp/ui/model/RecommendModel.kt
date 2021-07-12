package com.example.booksearchapp.ui.model

data class RecommendModel(
    val itemId: Int,
    val title: String = "",
    val author: String = "",
    val imageUrl: String? = null
): BaseModel