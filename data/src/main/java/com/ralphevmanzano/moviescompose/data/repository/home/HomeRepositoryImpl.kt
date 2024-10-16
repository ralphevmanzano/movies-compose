package com.ralphevmanzano.moviescompose.data.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ralphevmanzano.moviescompose.model.Movie
import com.ralphevmanzano.moviescompose.network.Dispatcher
import com.ralphevmanzano.moviescompose.network.MoviesDispatchers
import com.ralphevmanzano.moviescompose.network.paging_source.NowPlayingPagingSource
import com.ralphevmanzano.moviescompose.network.paging_source.PopularPagingSource
import com.ralphevmanzano.moviescompose.network.paging_source.TopRatedPagingSource
import com.ralphevmanzano.moviescompose.network.paging_source.UpcomingPagingSource
import com.ralphevmanzano.moviescompose.network.service.MoviesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class HomeRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    @Dispatcher(MoviesDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): HomeRepository {

    override fun getNowPlayingPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            NowPlayingPagingSource(moviesService)
        }.flow.flowOn(ioDispatcher)
    }

    override fun getPopularPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            PopularPagingSource(moviesService)
        }.flow.flowOn(ioDispatcher)
    }

    override fun getTopRatedPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            TopRatedPagingSource(moviesService)
        }.flow.flowOn(ioDispatcher)
    }

    override fun getUpcomingPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            UpcomingPagingSource(moviesService)
        }.flow.flowOn(ioDispatcher)
    }
}