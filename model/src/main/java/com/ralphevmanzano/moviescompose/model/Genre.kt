package com.ralphevmanzano.moviescompose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
)