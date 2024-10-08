package com.ralphevmanzano.moviescompose.data.repository.home

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.domain.model.MoviesWithCategory
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    @WorkerThread
    fun getAllMovies(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<MoviesWithCategory>

    @WorkerThread
    fun getNowPlaying(
        page: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>>

    @WorkerThread
    fun getPopular(
        page: Int, onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>>

    @WorkerThread
    fun getTopRated(
        page: Int, onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>>

    @WorkerThread
    fun getUpcoming(
        page: Int, onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Movie>>

    @WorkerThread
    fun getNowPlayingPaging(): Flow<PagingData<Movie>>

    @WorkerThread
    fun getPopularPaging(): Flow<PagingData<Movie>>

    @WorkerThread
    fun getTopRatedPaging(): Flow<PagingData<Movie>>

    @WorkerThread
    fun getUpcomingPaging(): Flow<PagingData<Movie>>
}