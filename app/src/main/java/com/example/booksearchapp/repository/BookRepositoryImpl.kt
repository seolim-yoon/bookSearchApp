package com.example.booksearchapp.repository

import androidx.paging.PagingSource
import com.example.booksearchapp.base.BaseRepository
import com.example.booksearchapp.data.BookService
import com.example.booksearchapp.data.database.dao.BookDao
import com.example.booksearchapp.data.database.model.BestSellerModel
import com.example.booksearchapp.data.response.BestSellerResult
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDao: BookDao, private val bookService: BookService): BookRepository, BaseRepository() {
    val domesticList = arrayListOf("ALL", "소설", "시/에세이", "예술/대중문화", "사회과학", "역사와 문화", "잡지", "만화", "유아", "아동", "가정과 생활")
    val foreignList = arrayListOf("ALL", "어린이", "ELT/사전", "문학", "경제경영/인문사회", "실용/예술", "해외잡지", "대학교재/전문서적", "컴퓨터", "일본도서", "프랑스도서")
    val recordList = arrayListOf("ALL", "가요", "Pop", "Rock", "J.POP", "월드뮤직", "Jazz", "클래식", "국악", "뉴에이지", "O.S.T")
    val dvdList = arrayListOf("ALL", "애니메이션", "영화", "블루레이", "유아동/교육 DVD", "", "", "", "", "", "")

    override fun getBestSellerResult(categoryId: String): Single<BestSellerResult> = bookService.getBestSellerBooks(key, categoryId)

    override fun insertAllBestSeller(bestSellerModels: List<BestSellerModel>) : Completable = bookDao.insertAllBestSeller(bestSellerModels)

    override fun getAllBestSellersByCategory(categoryId: String) : PagingSource<Int, BestSellerModel> = bookDao.getAllBestSellersByCategory(categoryId)

    override fun getBestSellersCategory(categoryId: String) : Single<List<String>> = bookDao.getBestSellersCategory(categoryId)
}