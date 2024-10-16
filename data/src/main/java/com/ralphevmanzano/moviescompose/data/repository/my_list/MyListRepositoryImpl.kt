package com.ralphevmanzano.moviescompose.data.repository.my_list

import com.ralphevmanzano.moviescompose.database.MovieDao
import com.ralphevmanzano.moviescompose.database.entity.mapper.toDomain
import com.ralphevmanzano.moviescompose.database.entity.mapper.toEntity
import com.ralphevmanzano.moviescompose.model.Movie
import com.ralphevmanzano.moviescompose.network.Dispatcher
import com.ralphevmanzano.moviescompose.network.MoviesDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MyListRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    @Dispatcher(MoviesDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): MyListRepository {

    override suspend fun addToMyList(movie: Movie) {
        withContext(ioDispatcher) {
            movieDao.addToMyList(movie.toEntity())
        }
    }

    override suspend fun removeFromMyList(movie: Movie) {
        withContext(ioDispatcher) {
            movieDao.removeFromMyList(movie.id)
        }
    }

    override fun getMyList(): Flow<List<Movie>> {
        return movieDao.getMyList().map { list ->
            list.map { it.toDomain() }
        }
    }
}