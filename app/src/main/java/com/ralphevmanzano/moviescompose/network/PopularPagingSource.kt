package com.ralphevmanzano.moviescompose.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.network.model.MoviesResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.isFailure
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onSuccess

class PopularPagingSource(
    private val moviesService: MoviesService
): PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            val movies = mutableListOf<Movie>()
            val totalPages: Int
            val response = moviesService.getPopular(currentPage)
            if (response.isSuccess) {
                val data = (response as ApiResponse.Success<MoviesResponse>).data
                movies.addAll(data.results)
                totalPages = data.totalPages
                LoadResult.Page(
                    data = movies,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (currentPage < totalPages) currentPage + 1 else null
                )
            } else {
                LoadResult.Error(Exception(response.messageOrNull))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}