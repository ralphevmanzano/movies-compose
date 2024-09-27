package com.ralphevmanzano.moviescompose.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphevmanzano.moviescompose.domain.model.Category
import com.ralphevmanzano.moviescompose.domain.model.Movie

@Composable
fun HomeScreen(
    onMovieClicked: (Movie) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
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
            LazyColumn {
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
                            fetchNextPage = { homeViewModel.fetchNextPage(category) }
                        )
                    }
                }
            }
        }
    }
}