package com.ralphevmanzano.moviescompose.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val moviesDispatchers: MoviesDispatchers)

enum class MoviesDispatchers {
    IO
}