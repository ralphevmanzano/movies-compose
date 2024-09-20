package com.ralphevmanzano.moviescompose.data.repository.details

import androidx.annotation.WorkerThread
import com.ralphevmanzano.moviescompose.domain.model.Genre
import com.ralphevmanzano.moviescompose.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    @WorkerThread
    fun getMovieDetails(id: Int): Flow<Movie>

    @WorkerThread
    fun getGenres(): Flow<List<Genre>>

}