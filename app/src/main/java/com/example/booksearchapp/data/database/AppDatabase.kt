package com.example.booksearchapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.booksearchapp.data.database.dao.BookDao
import com.example.booksearchapp.data.database.dao.HistoryDao
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.database.model.HistoryModel

@Database(entities = [BestSellerModel::class, HistoryModel::class], version = 15, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun historyDao(): HistoryDao
}