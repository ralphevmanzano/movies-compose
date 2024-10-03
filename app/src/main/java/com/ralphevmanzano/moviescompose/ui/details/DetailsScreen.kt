package com.ralphevmanzano.moviescompose.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphevmanzano.moviescompose.R
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.ui.components.GenreChip
import com.ralphevmanzano.moviescompose.ui.components.MoviePlaceHolder
import com.ralphevmanzano.moviescompose.ui.components.TextWithIcon
import com.ralphevmanzano.moviescompose.ui.theme.LightGray
import com.ralphevmanzano.moviescompose.ui.theme.MoviesComposeTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.util.Locale

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val uiState by detailsViewModel.uiState.collectAsStateWithLifecycle()
    val movie by detailsViewModel.movieDetails.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            if (uiState == DetailsUiState.Idle && movie != null) {
                GlideImage(
                    imageModel = { movie!!.backdropUrl },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter
                    ),
                    modifier = Modifier.aspectRatio(4f / 3f),
                    failure = {
                        MoviePlaceHolder()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = movie!!.title, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold)
                )
                MovieInfoSection(movie)
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                Text(text = movie!!.overview, modifier = Modifier.padding(horizontal = 16.dp))
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.languages),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(text = movie!!.spokenLanguages.joinToString(", ") { it.name })
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(R.string.status),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(text = movie!!.status)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp).fillMaxWidth())
            }
        }
        DetailsAppBar(onNavigateBack)
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun MovieInfoSection(movie: Movie?, onAddToList: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = movie!!.releaseYear, style = MaterialTheme.typography.titleMedium)
                Text(text = stringResource(R.string.bullet))
                Text(text = getRunTime(movie.runtime), style = MaterialTheme.typography.bodyLarge)
                Text(text = stringResource(R.string.bullet))
                TextWithIcon(
                    icon = {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Filled.Star,
                            tint = Color.Yellow,
                            contentDescription = "rating"
                        )
                    },
                    text = {
                        Text(
                            text = String.format(
                                Locale.getDefault(),
                                "%.1f",
                                movie.voteAverage
                            ),
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                )
                Text(
                    text = "(${String.format(Locale.getDefault(), "%,d", movie.voteCount)})",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            if (movie!!.genres.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    movie.genres.forEach {
                        GenreChip(genreName = it.name)
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .clickable { onAddToList() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add to list")
            Text(text = stringResource(R.string.my_list))
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailsAppBar(onNavigateBack: () -> Unit) {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(
                onClick = { onNavigateBack() },
                modifier = Modifier
                    .background(color = LightGray, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.Black,
                    contentDescription = "Back"
                )
            }
        },
    )
}

fun getRunTime(runtime: Int): String {
    val hrs = runtime / 60
    val min = runtime % 60
    return "${hrs}h ${min}m"
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    MoviesComposeTheme {
        DetailsScreen(
            onNavigateBack = {}
            /*Movie(
                adult = false,
                backdropPath = "/865DntZzOdX6rLMd405R0nFkLmL.jpg",
                id = 1,
                title = "The Garfield Movie",
                overview = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                posterPath = "/865DntZzOdX6rLMd405R0nFkLmL.jpg",
                releaseDate = "2023-07-10",
                voteAverage = 7.8f,
                voteCount = 1234,
                runtime = 124,
                genres = listOf(
                    Genre(1, "Action"),
                    Genre(2, "Adventure"),
                    Genre(3, "Comedy")
                ),
                spokenLanguages = listOf(Language(name = "English"), Language(name = "Filipino")),
                status = "Released"
            )*/
        )
    }
}