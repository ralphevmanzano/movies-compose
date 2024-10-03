package com.ralphevmanzano.moviescompose.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ralphevmanzano.moviescompose.data.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToMyList(movie: MovieEntity)

    @Query("SELECT * FROM my_list")
    fun getMyList(): Flow<List<MovieEntity>>

    @Query("DELETE FROM my_list WHERE id = :id")
    suspend fun removeFromMyList(id: Int)
}