package com.example.booksearchapp.data.paging

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.booksearchapp.data.database.model.BaseModel
import com.example.booksearchapp.data.response.mapper.SearchMapper
import com.example.booksearchapp.repository.SearchRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchBookPagingSource(
    private val repository: SearchRepository,
    private val keyword: String
) : RxPagingSource<Int, BaseModel>() {
    override fun getRefreshKey(state: PagingState<Int, BaseModel>): Int? = null

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, BaseModel>> {
        val nextPage: Int = params.key ?: 1
        return repository.searchBooksByName(keyword, nextPage)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, BaseModel>> { result ->
                LoadResult.Page(
                    data = SearchMapper().map(result),
                    prevKey = null,
                    nextKey = if(keyword == "") null else nextPage + 1
                )
            }
            .onErrorReturn { e ->
                Log.e("seolim", "error : " + e.message)
                LoadResult.Error(e)
            }
    }
}