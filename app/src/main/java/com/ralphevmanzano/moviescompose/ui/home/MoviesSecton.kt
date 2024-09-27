package com.ralphevmanzano.moviescompose.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ralphevmanzano.moviescompose.domain.model.Category
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.ui.theme.MoviesComposeTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun MoviesSection(
    category: Category,
    movieList: List<Movie>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    fetchNextPage: () -> Unit
) {

    Column(
        modifier = modifier
            .padding(top = 12.dp)
    ) {
        val scrollState = rememberLazyListState()
        val threshold = 6
        val shouldFetch by remember {
            derivedStateOf {
                if (movieList.isEmpty()) return@derivedStateOf false
                val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
                val shouldFetch = lastDisplayedIndex >= scrollState.layoutInfo.totalItemsCount - threshold
                return@derivedStateOf shouldFetch
            }
        }

        LaunchedEffect(key1 = shouldFetch) {
            if (shouldFetch) {
                println("Fetching, currentSize = ${scrollState.layoutInfo.totalItemsCount}")
                fetchNextPage()
            }
        }

        Text(
            text = category.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 12.dp)
        )
        LazyRow(
            state = scrollState,
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            // can't use key as the responses here have some duplicates
            items(movieList) { movie ->
                MovieItem(movie = movie)
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .size(106.dp, 164.dp)
            .testTag("Movie")
            .clickable {
                // TODO navigate to movie detail
            },
        shape = RoundedCornerShape(5.dp)
    ) {
        GlideImage(
            imageModel = { movie.posterUrl },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            component = rememberImageComponent {
                +ShimmerPlugin(
                    Shimmer.Resonate(
                        baseColor = Color.Transparent,
                        highlightColor = Color.LightGray,
                    ),
                )
            }
        )
    }
}

@Preview
@Composable
private fun MoviesSectionPreview() {
    MoviesComposeTheme {
        MoviesSection(
            category = Category.NOW_PLAYING,
            movieList = listOf(
                Movie(id = 1, posterPath = "/865DntZzOdX6rLMd405R0nFkLmL.jpg"),
                Movie(id = 2, posterPath = "/zQc1PITqFxZDbEmHlQgO5Mxc4Od.jpg"),
                Movie(id = 3, posterPath = "/qbkAqmmEIZfrCO8ZQAuIuVMlWoV.jpg")
            ),
            fetchNextPage = {}
        )
    }
}