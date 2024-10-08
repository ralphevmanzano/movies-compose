package com.ralphevmanzano.moviescompose.data.repository.search

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.ralphevmanzano.moviescompose.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    @WorkerThread
    fun searchMovies(query: String): Flow<PagingData<Movie>>
}