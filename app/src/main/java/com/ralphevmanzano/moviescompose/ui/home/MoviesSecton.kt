package com.ralphevmanzano.moviescompose.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.ui.theme.MoviesComposeTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun MoviesSection(
    title: String,
    movieList: List<Movie>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        LazyRow(
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(items = movieList, key = { _, movie -> movie.id }) { index, movie ->
                // TODO fetch next page
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
                +CrossfadePlugin()
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
            title = "Now Playing",
            movieList = listOf(
                Movie(id = 1, posterPath = "/865DntZzOdX6rLMd405R0nFkLmL.jpg"),
                Movie(id = 2, posterPath = "/zQc1PITqFxZDbEmHlQgO5Mxc4Od.jpg"),
                Movie(id = 3, posterPath = "/qbkAqmmEIZfrCO8ZQAuIuVMlWoV.jpg")
            )
        )
    }
}