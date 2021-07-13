package com.example.booksearchapp.data.database.model

import androidx.room.Entity

@Entity(tableName = "History")
data class HistoryModel(
        var keyword: String

) {

}