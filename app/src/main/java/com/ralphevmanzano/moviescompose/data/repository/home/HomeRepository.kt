package com.ralphevmanzano.moviescompose.data.repository.home

import androidx.annotation.WorkerThread
import com.ralphevmanzano.moviescompose.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    @WorkerThread
    fun getNowPlaying(page: Int): Flow<List<Movie>>

    @WorkerThread
    fun getPopular(page: Int): Flow<List<Movie>>

    @WorkerThread
    fun getTopRated(page: Int): Flow<List<Movie>>

    @WorkerThread
    fun getUpcoming(page: Int): Flow<List<Movie>>
}