package com.ralphevmanzano.moviescompose.data.repository.home

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.ralphevmanzano.moviescompose.model.Movie
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    @WorkerThread
    fun getNowPlayingPaging(): Flow<PagingData<Movie>>

    @WorkerThread
    fun getPopularPaging(): Flow<PagingData<Movie>>

    @WorkerThread
    fun getTopRatedPaging(): Flow<PagingData<Movie>>

    @WorkerThread
    fun getUpcomingPaging(): Flow<PagingData<Movie>>
}