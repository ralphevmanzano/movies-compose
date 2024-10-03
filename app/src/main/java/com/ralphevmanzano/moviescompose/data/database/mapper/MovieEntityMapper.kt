package com.ralphevmanzano.moviescompose.data.database.mapper

import com.ralphevmanzano.moviescompose.data.database.entity.MovieEntity
import com.ralphevmanzano.moviescompose.domain.model.Movie

object MovieEntityMapper: EntityMapper<Movie, MovieEntity> {

    override fun asDomain(entity: MovieEntity): Movie {
        return Movie(
            id = entity.id,
            genreIds = entity.genreIds,
            overview = entity.overview,
            posterPath = entity.posterPath,
            title = entity.title,
            releaseDate = entity.releaseDate,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount
        )
    }

    override fun asEntity(domain: Movie): MovieEntity {
        return MovieEntity(
            id = domain.id,
            genreIds = domain.genreIds,
            overview = domain.overview,
            posterPath = domain.posterPath,
            title = domain.title,
            releaseDate = domain.releaseDate,
            voteAverage = domain.voteAverage,
            voteCount = domain.voteCount
        )
    }
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntityMapper.asEntity(this)
}

fun MovieEntity.toDomain(): Movie {
    return MovieEntityMapper.asDomain(this)
}