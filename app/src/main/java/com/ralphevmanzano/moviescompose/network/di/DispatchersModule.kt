package com.ralphevmanzano.moviescompose.network.di

import com.ralphevmanzano.moviescompose.network.Dispatcher
import com.ralphevmanzano.moviescompose.network.MoviesDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

    @Provides
    @Dispatcher(MoviesDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}