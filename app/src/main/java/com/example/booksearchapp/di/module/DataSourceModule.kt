package com.example.booksearchapp.di.module

import com.example.booksearchapp.data.datasource.BookDataSource
import com.example.booksearchapp.data.datasource.BookDataSourceImpl
import com.example.booksearchapp.data.datasource.SearchDataSource
import com.example.booksearchapp.data.datasource.SearchDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataSourceModule {

    @Binds
    fun bindBookDataSource(searchDataSourceImpl: BookDataSourceImpl): BookDataSource

    @Binds
    fun bindSearchDataSource(searchDataSourceImpl: SearchDataSourceImpl): SearchDataSource
}