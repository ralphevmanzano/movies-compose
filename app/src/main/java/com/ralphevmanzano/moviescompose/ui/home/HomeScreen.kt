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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphevmanzano.moviescompose.domain.model.Movie

@Composable
fun HomeScreen(
    onMovieClicked: (Movie) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val allMovies by homeViewModel.allMovies.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState == HomeUiState.Loading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                item {
                    MoviesSection("Now Playing", allMovies.nowPlaying, modifier = Modifier.fillMaxWidth())
                }
                item {
                    MoviesSection("Popular", allMovies.popular, modifier = Modifier.fillMaxWidth())
                }
                item {
                    MoviesSection("Top Rated", allMovies.topRated, modifier = Modifier.fillMaxWidth())
                }
                item {
                    MoviesSection("Upcoming", allMovies.upcoming, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}