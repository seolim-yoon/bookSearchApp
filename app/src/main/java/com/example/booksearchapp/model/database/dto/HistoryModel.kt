package com.example.booksearchapp.model.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class HistoryModel(
        @PrimaryKey var keyword: String
)