package com.ralphevmanzano.moviescompose.domain.model

data class MoviesWithCategory(
    val featured: Movie,
    val nowPlaying: List<Movie> = emptyList(),
    val popular: List<Movie> = emptyList(),
    val topRated: List<Movie> = emptyList(),
    val upcoming: List<Movie> = emptyList()
)