package com.example.booksearchapp.repository

import android.app.Application
import androidx.paging.PagingSource
import com.example.booksearchapp.data.response.BestSellerResult
import com.example.booksearchapp.data.RetrofitBuilder
import com.example.booksearchapp.data.database.AppDatabase
import com.example.booksearchapp.data.database.dao.BookDao
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.SearchResult
import io.reactivex.Completable
import io.reactivex.Single

class BookRepository(application: Application) {
    private val key = "7D32361E8C549F8E4E42943E5937CC502D866279E38FB8BF4A02E636FC45DF2B"
    private val appDatabase = AppDatabase.getInstance(application)!!
    private val bookDao: BookDao = appDatabase.bookDao()

    val domesticList = arrayListOf<String>("ALL", "소설", "시/에세이", "예술/대중문화", "사회과학", "역사와 문화", "잡지", "만화", "유아", "아동", "가정과 생활")
    val foreignList = arrayListOf<String>("ALL", "어린이", "ELT/사전", "문학", "경제경영/인문사회", "실용/예술", "해외잡지", "대학교재/전문서적", "컴퓨터", "일본도서", "프랑스도서")
    val recordList = arrayListOf<String>("ALL", "가요", "Pop", "Rock", "J.POP", "월드뮤직", "Jazz", "클래식", "국악", "뉴에이지", "O.S.T")
    val dvdList = arrayListOf<String>("ALL", "애니메이션", "영화", "블루레이", "유아동/교육 DVD", "", "", "", "", "", "")

    fun searchBooksByName(query: String, page: Int) : Single<SearchResult> = RetrofitBuilder.service.searchBooksByName(key, query, page);

    fun getBestSellerResult(categoryId: String): Single<BestSellerResult> = RetrofitBuilder.service.getBestSellerBooks(key, categoryId)

    fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>) : Completable = bookDao.insertAllBestSeller(bestSellerModels)

    fun getAllBestSellersByCategory(categoryId: String) : PagingSource<Int, BestSellerModel> = bookDao.getAllBestSellersByCategory(categoryId)

    fun getBestSellersCategory(categoryId: String) : Single<List<String>> = bookDao.getBestSellersCategory(categoryId)
}