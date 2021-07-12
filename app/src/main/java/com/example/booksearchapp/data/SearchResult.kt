package com.example.booksearchapp.data

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("title")
    val title: String,
) {
}