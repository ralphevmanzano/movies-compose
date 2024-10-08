package com.ralphevmanzano.moviescompose.data.repository.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ralphevmanzano.moviescompose.data.repository.handleApiResponse
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.domain.model.MoviesWithCategory
import com.ralphevmanzano.moviescompose.network.Dispatcher
import com.ralphevmanzano.moviescompose.network.MoviesDispatchers
import com.ralphevmanzano.moviescompose.network.MoviesService
import com.ralphevmanzano.moviescompose.network.NowPlayingPagingSource
import com.ralphevmanzano.moviescompose.network.PopularPagingSource
import com.ralphevmanzano.moviescompose.network.TopRatedPagingSource
import com.ralphevmanzano.moviescompose.network.UpcomingPagingSource
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach


class HomeRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    @Dispatcher(MoviesDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): HomeRepository {

    override fun getAllMovies(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<MoviesWithCategory> = flow {
        try {
            coroutineScope {
                val (nowPlaying, popular, topRated, upcoming) = listOf(
                    async { moviesService.getNowPlaying(1) },
                    async { moviesService.getPopular(1) },
                    async { moviesService.getTopRated(1) },
                    async { moviesService.getUpcoming(1) }
                ).awaitAll().map { handleApiResponse(it)?.results.orEmpty() }

                val featured = nowPlaying.random()
                val nowPlayingMovies = nowPlaying.filter { it != featured }

                val moviesWithCategory = MoviesWithCategory(
                    featured = featured,
                    nowPlaying = nowPlayingMovies,
                    popular = popular,
                    topRated = topRated,
                    upcoming = upcoming
                )

                emit(moviesWithCategory)
            }
        } catch (e: Exception) {
            onError(e.message)
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getNowPlaying(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>> = flow {
        val response = moviesService.getNowPlaying(page)
        response.suspendOnSuccess {
            val movies = data.results
            emit(movies)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getPopular(
        page: Int, onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>> = flow {
        val response = moviesService.getPopular(page)
        response.suspendOnSuccess {
            val movies = data.results
            emit(movies)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getTopRated(
        page: Int, onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>> = flow {
        val response = moviesService.getTopRated(page)
        response.suspendOnSuccess {
            val movies = data.results
            emit(movies)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getUpcoming(
        page: Int, onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>> = flow {
        val response = moviesService.getUpcoming(page)
        response.suspendOnSuccess {
            val movies = data.results
            emit(movies)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getNowPlayingPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            NowPlayingPagingSource(moviesService)
        }.flow
    }

    override fun getPopularPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            PopularPagingSource(moviesService)
        }.flow
    }

    override fun getTopRatedPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            TopRatedPagingSource(moviesService)
        }.flow
    }

    override fun getUpcomingPaging(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)) {
            UpcomingPagingSource(moviesService)
        }.flow
    }
}