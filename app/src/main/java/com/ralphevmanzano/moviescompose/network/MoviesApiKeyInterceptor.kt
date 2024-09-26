package com.ralphevmanzano.moviescompose.network

import com.ralphevmanzano.moviescompose.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MoviesApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val apiKey = BuildConfig.TMDB_API_KEY
        val url = original.url.newBuilder().addQueryParameter("api_key", apiKey).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}