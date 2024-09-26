package com.ralphevmanzano.moviescompose.data.repository.home

import com.ralphevmanzano.moviescompose.data.repository.handleApiResponse
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.domain.model.MoviesWithCategory
import com.ralphevmanzano.moviescompose.network.Dispatcher
import com.ralphevmanzano.moviescompose.network.MoviesDispatchers
import com.ralphevmanzano.moviescompose.network.MoviesService
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
                val nowPlayingDeferred = async { moviesService.getNowPlaying(1) }
                val popularDeferred = async { moviesService.getPopular(1) }
                val topRatedDeferred = async { moviesService.getTopRated(1) }
                val upcomingDeferred = async { moviesService.getUpcoming(1) }

                val nowPlaying = handleApiResponse(nowPlayingDeferred.await())?.results.orEmpty()
                val popular = handleApiResponse(popularDeferred.await())?.results.orEmpty()
                val topRated = handleApiResponse(topRatedDeferred.await())?.results.orEmpty()
                val upcoming = handleApiResponse(upcomingDeferred.await())?.results.orEmpty()

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
        val response = moviesService.getNowPlaying(page)
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
        val response = moviesService.getNowPlaying(page)
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
        val response = moviesService.getNowPlaying(page)
        response.suspendOnSuccess {
            val movies = data.results
            emit(movies)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}