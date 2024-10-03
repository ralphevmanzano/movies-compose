package com.ralphevmanzano.moviescompose.data.di

import android.app.Application
import androidx.room.Room
import com.ralphevmanzano.moviescompose.data.database.GenreIdsConverter
import com.ralphevmanzano.moviescompose.data.database.MovieDatabase
import com.ralphevmanzano.moviescompose.data.database.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application, genreIdsConverter: GenreIdsConverter): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, "movie_db")
            .fallbackToDestructiveMigration()
            .addTypeConverter(genreIdsConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}