package com.ralphevmanzano.moviescompose.data.di

import com.ralphevmanzano.moviescompose.data.repository.details.DetailsRepository
import com.ralphevmanzano.moviescompose.data.repository.details.DetailsRepositoryImpl
import com.ralphevmanzano.moviescompose.data.repository.home.HomeRepository
import com.ralphevmanzano.moviescompose.data.repository.home.HomeRepositoryImpl
import com.ralphevmanzano.moviescompose.data.repository.my_list.MyListRepository
import com.ralphevmanzano.moviescompose.data.repository.my_list.MyListRepositoryImpl
import com.ralphevmanzano.moviescompose.data.repository.search.SearchRepository
import com.ralphevmanzano.moviescompose.data.repository.search.SearchRepositoryImpl
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

    @Binds
    fun bindsSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    fun bindsMyListRepository(myListRepositoryImpl: MyListRepositoryImpl): MyListRepository
}