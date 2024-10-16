package com.ralphevmanzano.moviescompose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.ralphevmanzano.moviescompose.model.Category
import com.ralphevmanzano.moviescompose.model.Movie
import com.ralphevmanzano.moviescompose.designsystem.R
import com.ralphevmanzano.moviescompose.designsystem.component.MoviesAppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val myList by homeViewModel.myList.collectAsStateWithLifecycle()

    val nowPlayingPaging = homeViewModel.nowPlayingPaging.collectAsLazyPagingItems()
    val popularPaging = homeViewModel.popularPaging.collectAsLazyPagingItems()
    val topRatedPaging = homeViewModel.topRatedPaging.collectAsLazyPagingItems()
    val upcomingPaging = homeViewModel.upcomingPaging.collectAsLazyPagingItems()

    LaunchedEffect(
        nowPlayingPaging.loadState,
        popularPaging.loadState,
        topRatedPaging.loadState,
        upcomingPaging.loadState
    ) {
        homeViewModel.consumeNowPlayingLoadState(nowPlayingPaging.loadState)
        homeViewModel.consumePopularLoadState(popularPaging.loadState)
        homeViewModel.consumeTopRatedLoadState(topRatedPaging.loadState)
        homeViewModel.consumeUpcomingLoadState(upcomingPaging.loadState)
    }

    LaunchedEffect(nowPlayingPaging.itemSnapshotList) {
        homeViewModel.generateFeaturedMovie(nowPlayingPaging.itemSnapshotList)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    FeaturedSection(
                        movie = uiState.featured ?: Movie(),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        isAddedToMyList = myList.any { it.id == uiState.featured?.id },
                        onInfoClick = { movieId ->
                            onMovieClicked(movieId)
                        },
                        isLoading = uiState.featured == null,
                        onAddToMyList = { movie ->
                            homeViewModel.addToMyList(movie)
                        }
                    )
                }

                val categoriesWithMovies = listOf(
                    Category.NOW_PLAYING to (nowPlayingPaging to uiState.isLoadingNowPlaying),
                    Category.UPCOMING to (upcomingPaging to uiState.isLoadingUpcoming),
                    Category.POPULAR to (popularPaging to uiState.isLoadingPopular),
                    Category.TOP_RATED to (topRatedPaging to uiState.isLoadingTopRated)
                )

                categoriesWithMovies.forEach { (category, movies) ->
                    item {
                        MoviesSection(
                            category = category,
                            movieList = movies.first,
                            modifier = Modifier.fillMaxWidth(),
                            isLoading = movies.second,
                            onMovieClicked = { movieId ->
                                onMovieClicked(movieId)
                            }
                        )
                    }
                }

                item {
                    PoweredBySection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
        MoviesAppBar(stringResource(R.string.movies))
    }
}