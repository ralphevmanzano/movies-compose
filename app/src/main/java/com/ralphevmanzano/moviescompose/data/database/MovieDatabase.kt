package com.ralphevmanzano.moviescompose.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ralphevmanzano.moviescompose.data.database.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = true)
@TypeConverters(value = [GenreIdsConverter::class])
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}