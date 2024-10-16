package com.ralphevmanzano.moviescompose.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.ralphevmanzano.moviescompose.model.Movie
import com.ralphevmanzano.moviescompose.designsystem.component.MoviePlaceHolder
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.ralphevmanzano.moviescompose.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Int) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    var text by rememberSaveable { mutableStateOf("") }
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val searchResults = searchViewModel.searchPaging.collectAsLazyPagingItems()

    val coroutineScope = rememberCoroutineScope()
    var job: Job? = null

    fun doSearch() {
        job?.cancel()
        job = coroutineScope.launch {
            delay(1000)
            searchViewModel.onSearchChanged(text)
        }
    }

    LaunchedEffect(searchResults.loadState) {
        searchViewModel.consumeSearchLoadState(searchResults)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = text,
                    onQueryChange = {
                        text = it
                        doSearch()
                    },
                    onSearch = { doSearch() },
                    placeholder = { Text(stringResource(R.string.search_movies)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (uiState == SearchUiState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    text = ""
                                    searchViewModel.onSearchChanged(text)
                                }
                            )
                        }
                    },
                    expanded = false,
                    onExpandedChange = {}
                )
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            expanded = false,
            onExpandedChange = {},
            content = {}
        )

        if (searchResults.itemCount > 0) {
            LazyColumn(
                contentPadding = PaddingValues(top = 72.dp)
            ) {
                items(searchResults.itemCount) { index ->
                    val movie = searchResults[index]
                    if (movie != null) {
                        SearchItem(
                            movie = movie,
                            modifier = Modifier.fillMaxWidth(),
                            onMovieClicked = onMovieClicked
                        )
                        if (index < searchResults.itemSnapshotList.lastIndex) {
                            HorizontalDivider(thickness = 1.dp)
                        }
                    }
                }
            }
        } else if (uiState == SearchUiState.NoResults) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 96.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.oh_darn_we_don_t_have_that),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.try_searching_for_another_movie))
            }
        }
    }
}

@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Int) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMovieClicked(movie.id) }
    ) {
        GlideImage(
            modifier = Modifier
                .height(120.dp)
                .aspectRatio(4f / 3f),
            imageModel = { movie.backdropUrl.ifBlank { movie.posterUrl } },
            loading = {
                MoviePlaceHolder()
            },
            requestBuilder = {
                Glide
                    .with(LocalContext.current)
                    .asBitmap()
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .thumbnail(0.5f)
                    .transition(withCrossFade())
            },
            failure = {
                MoviePlaceHolder()
            }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = movie.title + " (${movie.releaseYear})",
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = movie.overview,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}