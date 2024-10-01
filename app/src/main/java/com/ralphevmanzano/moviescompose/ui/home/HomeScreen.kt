package com.ralphevmanzano.moviescompose.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphevmanzano.moviescompose.domain.model.Category

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val allMovies by homeViewModel.allMovies.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = homeViewModel) {
        homeViewModel.fetchAllMovies()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState is HomeUiState.Loading && (uiState as HomeUiState.Loading).category == null) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    FeaturedSection(
                        movie = allMovies.featured,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }

                val categoriesWithMovies = listOf(
                    Category.NOW_PLAYING to allMovies.nowPlaying,
                    Category.UPCOMING to allMovies.upcoming,
                    Category.POPULAR to allMovies.popular,
                    Category.TOP_RATED to allMovies.topRated
                )

                categoriesWithMovies.forEach { (category, movies) ->
                    item {
                        MoviesSection(
                            category = category,
                            movieList = movies,
                            modifier = Modifier.fillMaxWidth(),
                            isLoading = uiState is HomeUiState.Loading && (uiState as HomeUiState.Loading).category == category,
                            fetchNextPage = { homeViewModel.fetchNextPage(category) },
                            onMovieClicked = { movieId ->
                                onMovieClicked(movieId)
                            }
                        )
                    }
                }
            }
        }
    }
}