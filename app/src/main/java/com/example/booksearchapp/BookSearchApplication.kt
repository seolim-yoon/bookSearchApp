package com.example.booksearchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookSearchApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}