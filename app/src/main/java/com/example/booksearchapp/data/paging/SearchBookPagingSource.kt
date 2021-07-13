package com.example.booksearchapp.data.paging

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.booksearchapp.data.database.model.BaseModel
import com.example.booksearchapp.data.response.transformSearchModel
import com.example.booksearchapp.repository.BookRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchBookPagingSource(
    private val repository: BookRepository,
    private val query: String
) : RxPagingSource<Int, BaseModel>() {
    override fun getRefreshKey(state: PagingState<Int, BaseModel>): Int? = null

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, BaseModel>> {
        val nextPage: Int = params.key ?: 1
        return repository.searchBooksByName(query, nextPage)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, BaseModel>> { result ->
                Log.v("seolim", "nextPage : " + nextPage)
                Log.v("seolim", "loadSize : " + params.loadSize)
                LoadResult.Page(
                    data = result.transformSearchModel(),
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