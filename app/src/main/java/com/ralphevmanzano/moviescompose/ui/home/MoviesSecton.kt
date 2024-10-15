package com.ralphevmanzano.moviescompose.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ralphevmanzano.moviescompose.model.Category
import com.ralphevmanzano.moviescompose.model.Movie
import com.ralphevmanzano.moviescompose.ui.theme.MoviesComposeTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerContainer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import kotlinx.coroutines.flow.flowOf

@Composable
fun MoviesSection(
    modifier: Modifier = Modifier,
    category: Category,
    movieList: LazyPagingItems<Movie>,
    isLoading: Boolean = false,
    onMovieClicked: (Int) -> Unit
) {

    Column(
        modifier = modifier
            .padding(top = 12.dp)
    ) {
        val scrollState = rememberLazyListState()

        Text(
            text = category.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 12.dp)
        )

        if (movieList.itemCount == 0) {
            MovieSectionShimmer(modifier = Modifier.padding(top = 4.dp))
        } else {
            LazyRow(
                state = scrollState,
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                items(movieList.itemCount) { index ->
                    val movie = movieList[index]
                    if (movie != null) {
                        MovieItem(
                            modifier = Modifier.size(106.dp, 164.dp)
                                .testTag("Movie")
                                .clickable {
                                    onMovieClicked(movie.id)
                                },
                            movie = movie,
                        )
                    }
                }

                if (isLoading) {
                    item {
                        MovieItemShimmer(modifier = Modifier.size(106.dp, 164.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Card(
        modifier = modifier,
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

@Composable
fun MovieSectionShimmer(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(4) {
            MovieItemShimmer(modifier = Modifier.size(106.dp, 164.dp))
        }
    }
}

@Composable
private fun MovieItemShimmer(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(5.dp)
    ) {
        ShimmerContainer(
            modifier = Modifier.fillMaxSize(),
            shimmer = Shimmer.Fade(
                baseColor = Color.White,
                highlightColor = Color.LightGray,
            )
        )
    }
}

@Preview
@Composable
private fun MoviesSectionPreview() {
    val emptyPagingData = flowOf(PagingData.empty<Movie>())

    // Convert it to LazyPagingItems
    val lazyPagingItems = emptyPagingData.collectAsLazyPagingItems()

    MoviesComposeTheme {
        MoviesSection(
            category = Category.NOW_PLAYING,
            movieList = lazyPagingItems,
            onMovieClicked = {}
        )
    }
}