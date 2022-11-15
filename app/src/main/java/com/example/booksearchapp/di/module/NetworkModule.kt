package com.example.booksearchapp.di.module

import com.example.booksearchapp.model.network.api.BookServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL: String = "http://book.interpark.com/"

    @Provides
    fun provideBaseURL() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(provideBaseURL())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideBookSearchApi(retrofit: Retrofit): BookServiceApi = retrofit.create(BookServiceApi::class.java)
}