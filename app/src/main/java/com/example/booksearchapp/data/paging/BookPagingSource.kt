package com.example.booksearchapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.booksearchapp.data.transformBestSellerModel
import com.example.booksearchapp.repository.BookRepository
import com.example.booksearchapp.ui.model.BaseModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BookPagingSource(private val repository: BookRepository) : RxPagingSource<Int, BaseModel>() {
    override fun getRefreshKey(state: PagingState<Int, BaseModel>): Int? = null

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, BaseModel>> {
        val nextPage: Int = params.key ?: 1
        return repository.getBestSellerResult()
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, BaseModel>> { result ->
                Log.v("seolim", "nextPage : " + nextPage)
                Log.v("seolim", "loadSize : " + params.loadSize)
                LoadResult.Page(
                    data = result.transformBestSellerModel(),
                    prevKey = null,
                    nextKey = nextPage + 1
                )
            }
            .onErrorReturn { e ->
                Log.e("seolim", "error : " + e.message)
                LoadResult.Error(e)
            }
    }
}