package com.example.booksearchapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.booksearchapp.model.database.dao.BookDao
import com.example.booksearchapp.model.database.dao.HistoryDao
import com.example.booksearchapp.model.database.dto.BestSellerModel
import com.example.booksearchapp.model.database.dto.HistoryModel

@Database(entities = [BestSellerModel::class, HistoryModel::class], version = 15, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun historyDao(): HistoryDao
}