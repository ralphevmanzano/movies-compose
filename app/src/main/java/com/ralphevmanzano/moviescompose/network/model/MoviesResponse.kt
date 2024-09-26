package com.ralphevmanzano.moviescompose.network.model

import com.ralphevmanzano.moviescompose.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("page")
    val page: Int = 0,
    @SerialName("results")
    val results: List<Movie> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0
)