package com.ralphevmanzano.moviescompose.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ralphevmanzano.moviescompose.designsystem.R
import com.ralphevmanzano.moviescompose.designsystem.theme.LightGray
import com.ralphevmanzano.moviescompose.designsystem.theme.MoviesComposeTheme
import com.ralphevmanzano.moviescompose.model.Genre
import com.ralphevmanzano.moviescompose.model.Movie
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FeaturedSection(
    modifier: Modifier = Modifier,
    movie: Movie,
    isAddedToMyList: Boolean,
    isLoading: Boolean,
    onInfoClick: (Int) -> Unit,
    onAddToMyList: (Movie) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Box {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .aspectRatio(3f / 4f)
                        .background(color = Color.LightGray)
                )
            } else {
                GlideImage(
                    modifier = Modifier.aspectRatio(3f / 4f),
                    imageModel = { movie.posterUrl },
                    imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        movie.genres.forEachIndexed { index, genre ->
                            Text(text = genre.name)
                            if (index < movie.genres.size - 1 && movie.genres.size > 1) {
                                Text(text = stringResource(R.string.bullet))
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(1f),
                            onClick = { onInfoClick(movie.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(5),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.info), color = Color.Black)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { onAddToMyList(movie) },
                            colors = ButtonDefaults.buttonColors(containerColor = LightGray),
                            shape = RoundedCornerShape(5),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isAddedToMyList) Icons.Default.Check else Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = stringResource(R.string.my_list))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeaturedSectionPreview() {
    MoviesComposeTheme {
        FeaturedSection(
            movie = Movie(
                id = 1,
                posterPath = "/865DntZzOdX6rLMd405R0nFkLmL.jpg",
                genres = listOf(
                    Genre(1, "Action"),
                    Genre(2, "Adventure"),
                    Genre(3, "Comedy")
                )
            ),
            isLoading = true,
            isAddedToMyList = false,
            onInfoClick = {},
            onAddToMyList = {}
        )
    }
}
