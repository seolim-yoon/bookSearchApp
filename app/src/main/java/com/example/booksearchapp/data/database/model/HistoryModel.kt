package com.example.booksearchapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class HistoryModel(
        @PrimaryKey var keyword: String
)