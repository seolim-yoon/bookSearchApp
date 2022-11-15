package com.example.booksearchapp.di.module

import com.example.booksearchapp.repository.BookRepository
import com.example.booksearchapp.repository.BookRepositoryImpl
import com.example.booksearchapp.repository.SearchRepository
import com.example.booksearchapp.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindBookRepository(searchRepositoryImpl: BookRepositoryImpl): BookRepository

    @Binds
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}