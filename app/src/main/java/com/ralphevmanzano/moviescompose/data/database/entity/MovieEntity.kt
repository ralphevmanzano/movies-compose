package com.ralphevmanzano.moviescompose.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "my_list", indices = [Index(value = ["id"], unique = true)])
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("genre_ids")
    val genreIds: List<Int>,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("poster_path")
    val posterPath: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("vote_average")
    val voteAverage: Float,
    @ColumnInfo("vote_count")
    val voteCount: Int
)