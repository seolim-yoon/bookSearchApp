package com.example.booksearchapp.base

import android.app.Application
import com.example.booksearchapp.data.database.AppDatabase

open class BaseRepository(application: Application) {
    // Open API Key
    open val key = "7D32361E8C549F8E4E42943E5937CC502D866279E38FB8BF4A02E636FC45DF2B"
    open val appDatabase = AppDatabase.getInstance(application)!!
}