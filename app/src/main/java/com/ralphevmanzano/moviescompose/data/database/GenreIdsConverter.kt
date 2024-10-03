package com.ralphevmanzano.moviescompose.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@ProvidedTypeConverter
class GenreIdsConverter @Inject constructor(
    private val json: Json
) {

    @TypeConverter
    fun fromGenreIds(genreIds: List<Int>): String {
        return json.encodeToString(genreIds)
    }

    @TypeConverter
    fun toGenreIds(genreIdsString: String): List<Int> {
        return json.decodeFromString(genreIdsString)
    }
}