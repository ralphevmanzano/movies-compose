package com.ralphevmanzano.moviescompose.data.repository.my_list

import com.ralphevmanzano.moviescompose.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MyListRepository {
    suspend fun addToMyList(movie: Movie)
    suspend fun removeFromMyList(movie: Movie)
    fun getMyList(): Flow<List<Movie>>
}