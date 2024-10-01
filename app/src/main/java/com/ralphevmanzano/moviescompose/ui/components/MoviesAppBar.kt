package com.ralphevmanzano.moviescompose.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesAppBar(
    title: String,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    )
}

@Preview
@Composable
fun MoviesAppBarPreview() {
    MoviesAppBar(title = "Movies")
}