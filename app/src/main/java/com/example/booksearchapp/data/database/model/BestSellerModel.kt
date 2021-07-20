package com.example.booksearchapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BestSeller")
data class BestSellerModel(
        @PrimaryKey val itemId: Int,
        val rank: Int = 0,
        val title: String = "",
        val author: String = "",
        val imageUrlLarge: String? = null,
        val imageUrlSmall: String? = null,
        val categoryId: String = "100",
        val categoryName: String = "국내도서",
        val priceStandard: Int = 0,
        val saleStatus: String = "",
        val description: String = ""
): BaseModel