package com.ralphevmanzano.moviescompose.data.repository.details

import com.ralphevmanzano.moviescompose.model.Genre
import com.ralphevmanzano.moviescompose.model.Movie
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

class DetailsRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService,
    @Dispatcher(MoviesDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : DetailsRepository {

    override fun getMovieDetails(
        id: Int, onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<Movie> = flow {
        val response = moviesService.getMovieDetails(id)
        response.suspendOnSuccess {
            emit(data)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getGenres(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Genre>> = flow {
        val response = moviesService.getGenres()
        response.suspendOnSuccess {
            emit(data.genres)
        }.onFailure {
            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

}