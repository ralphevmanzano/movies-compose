package com.ralphevmanzano.moviescompose.data.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ralphevmanzano.moviescompose.model.Movie
import com.ralphevmanzano.moviescompose.network.Dispatcher
import com.ralphevmanzano.moviescompose.network.MoviesDispatchers
import com.ralphevmanzano.moviescompose.network.paging_source.SearchPagingSource
import com.ralphevmanzano.moviescompose.network.service.MoviesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    @Dispatcher(MoviesDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : SearchRepository {

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            SearchPagingSource(query, moviesService)
        }.flow.flowOn(ioDispatcher)
    }
}