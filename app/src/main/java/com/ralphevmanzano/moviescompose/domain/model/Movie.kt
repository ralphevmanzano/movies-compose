package com.ralphevmanzano.moviescompose.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Serializable
data class Movie(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerialName("genres")
    val genres: List<Genre> = emptyList(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("overview")
    val overview: String = "",
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("vote_average")
    val voteAverage: Float = 0f,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("homepage")
    val homePage: String = "",
    @SerialName("runtime")
    val runtime: Int = 0,
    @SerialName("spoken_languages")
    val spokenLanguages: List<Language> = emptyList(),
    @SerialName("status")
    val status: String = "",
    @SerialName("tagline")
    val tagLine: String = ""
) {
    enum class Type {
        NOW_PLAYING,
        POPULAR,
        TOP_RATED,
        UPCOMING
    }

    val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w500${posterPath}"

    val backdropUrl: String
        get() = "https://image.tmdb.org/t/p/w500${backdropPath}"

    val releaseYear: String
        get() {
            return try {
                val localDate = LocalDate.parse(releaseDate)
                localDate.year.toString()
            } catch (e: DateTimeParseException) {
                e.printStackTrace()
                ""
            }
        }

    var totalResults = 0
}