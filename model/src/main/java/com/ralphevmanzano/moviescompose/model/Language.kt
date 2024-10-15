package com.ralphevmanzano.moviescompose.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("english_name")
    val englishName: String = "",
    @SerialName("iso_639_1")
    val code: String = "",
    @SerialName("name")
    val name: String = ""
)