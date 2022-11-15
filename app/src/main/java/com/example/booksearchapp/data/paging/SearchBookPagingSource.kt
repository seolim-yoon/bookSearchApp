package com.example.booksearchapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.booksearchapp.data.database.model.BaseModel
import com.example.booksearchapp.data.database.model.SearchModel
import com.example.booksearchapp.data.response.mapper.SearchMapper
import com.example.booksearchapp.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class SearchBookPagingSource (
    private val repository: SearchRepository,
    private val keyword: String
) : PagingSource<Int, BaseModel>() {
    override fun getRefreshKey(state: PagingState<Int, BaseModel>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BaseModel> {
        return try {
            val nextPage: Int = params.key ?: 1

            val response = withContext(Dispatchers.IO) {
                repository.searchBooksByName(keyword, nextPage)
            }

            LoadResult.Page(
                data = SearchMapper().map(response.body()) ?: listOf(),
                prevKey = null,
                nextKey = if(keyword == "") null else nextPage + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}