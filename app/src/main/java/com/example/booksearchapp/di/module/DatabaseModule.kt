package com.example.booksearchapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.booksearchapp.model.database.AppDatabase
import com.example.booksearchapp.model.database.dao.BookDao
import com.example.booksearchapp.model.database.dao.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "book_db.db")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideBookDao(appDatabase: AppDatabase): BookDao = appDatabase.bookDao()

    @Singleton
    @Provides
    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao = appDatabase.historyDao()
}