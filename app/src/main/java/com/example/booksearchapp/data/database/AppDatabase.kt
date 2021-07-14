package com.example.booksearchapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.booksearchapp.data.database.dao.BookDao
import com.example.booksearchapp.data.database.model.BestSellerModel

@Database(entities = [BestSellerModel::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "book_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}