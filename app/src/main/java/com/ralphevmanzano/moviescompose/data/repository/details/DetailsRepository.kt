package com.ralphevmanzano.moviescompose.data.repository.details

import androidx.annotation.WorkerThread
import com.ralphevmanzano.moviescompose.model.Genre
import com.ralphevmanzano.moviescompose.model.Movie
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    @WorkerThread
    fun getMovieDetails(
        id: Int,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Movie>

    @WorkerThread
    fun getGenres(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Genre>>

}