package com.example.booksearchapp.repository

import android.app.Application
import androidx.paging.PagingSource
import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.RetrofitBuilder
import com.example.booksearchapp.data.database.AppDatabase
import com.example.booksearchapp.data.database.dao.BookDao
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.SearchResult
import io.reactivex.Single

class BookRepository(application: Application) {
    private val key = "7D32361E8C549F8E4E42943E5937CC502D866279E38FB8BF4A02E636FC45DF2B"
    private val appDatabase = AppDatabase.getInstance(application)!!
    private val bookDao: BookDao = appDatabase.bookDao()

    val domesticList = arrayListOf<String>("ALL", "소설", "시/에세이", "예술/대중문화", "사회과학", "역사와 문화", "잡지", "만화", "유아", "아동", "가정과 생활")
    val foreignList = arrayListOf<String>("ALL", "어린이", "ELT/사전", "문학", "경영/인문", "예술/디자인", "실용", "해외잡지", "대학교재", "컴퓨터", "일본도서")
    val recordList = arrayListOf<String>("ALL", "가요", "Pop", "Rock", "일본음악", "World Music", "Jazz", "클래식", "국악", "명상", "O.S.T")
    val dvdList = arrayListOf<String>("ALL", "애니메이션", "다큐멘터리", "TV 시리즈", "건강/스포츠", "영화", "해외구매", "기타", "", "", "")

    fun searchBooksByName(query: String, page: Int) : Single<SearchResult> = RetrofitBuilder.service.searchBooksByName(key, query, page);

    fun getBestSellerResult(categoryId: String): Single<BestSellerResult> = RetrofitBuilder.service.getBestSellerBooks(key, categoryId)

    fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>) = bookDao.insertAllBestSeller(bestSellerModels)

    fun getAllBestSellersByCategory(categoryId: String) : PagingSource<Int, BestSellerModel> = bookDao.getAllBestSellersByCategory(categoryId)

}