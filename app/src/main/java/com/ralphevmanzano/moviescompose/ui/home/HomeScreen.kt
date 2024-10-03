package com.ralphevmanzano.moviescompose.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphevmanzano.moviescompose.R
import com.ralphevmanzano.moviescompose.domain.model.Category
import com.ralphevmanzano.moviescompose.ui.components.MoviesAppBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val allMovies by homeViewModel.allMovies.collectAsStateWithLifecycle()
    val myList by homeViewModel.myList.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = homeViewModel) {
        homeViewModel.fetchAllMovies()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 56.dp),
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
                            modifier = Modifier.padding(horizontal = 12.dp),
                            isAddedToMyList = myList.any { it.id == allMovies.featured.id },
                            onInfoClick = { movieId ->
                                onMovieClicked(movieId)
                            },
                            onAddToMyList = { movie ->
                                homeViewModel.addToMyList(movie)
                            }
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
        MoviesAppBar(stringResource(R.string.movies))
    }
}