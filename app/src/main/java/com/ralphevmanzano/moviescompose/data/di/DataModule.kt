package com.ralphevmanzano.moviescompose.data.di

import com.ralphevmanzano.moviescompose.data.repository.details.DetailsRepository
import com.ralphevmanzano.moviescompose.data.repository.details.DetailsRepositoryImpl
import com.ralphevmanzano.moviescompose.data.repository.home.HomeRepository
import com.ralphevmanzano.moviescompose.data.repository.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    fun bindsDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository
}