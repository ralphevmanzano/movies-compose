package com.ralphevmanzano.moviescompose.data.network.model

import com.ralphevmanzano.moviescompose.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    @SerialName("genres")
    val genres: List<Genre> = emptyList()
)