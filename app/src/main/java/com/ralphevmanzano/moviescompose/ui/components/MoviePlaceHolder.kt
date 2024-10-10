package com.ralphevmanzano.moviescompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ralphevmanzano.moviescompose.R
import com.ralphevmanzano.moviescompose.ui.theme.MoviesComposeTheme

@Composable
fun MoviePlaceHolder(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(color = Color.DarkGray)) {
        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .size(56.dp),
            painter = painterResource(id = R.drawable.tmdb_placeholder),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun MoviePlaceHolderPreview() {
    MoviesComposeTheme {
        MoviePlaceHolder(modifier = Modifier.fillMaxWidth())
    }
}