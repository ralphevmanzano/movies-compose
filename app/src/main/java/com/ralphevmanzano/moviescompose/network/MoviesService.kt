package com.ralphevmanzano.moviescompose.network

import com.ralphevmanzano.moviescompose.model.Movie
import com.ralphevmanzano.moviescompose.network.model.GenresResponse
import com.ralphevmanzano.moviescompose.network.model.MoviesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int): ApiResponse<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopular(@Query("page") page: Int): ApiResponse<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("page") page: Int): ApiResponse<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(@Query("page") page: Int): ApiResponse<MoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int): ApiResponse<MoviesResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): ApiResponse<Movie>

    @GET("genre/movie/list")
    suspend fun getGenres(): ApiResponse<GenresResponse>
}