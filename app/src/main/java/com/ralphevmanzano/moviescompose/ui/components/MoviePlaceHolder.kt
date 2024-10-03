package com.ralphevmanzano.moviescompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ralphevmanzano.moviescompose.R

@Composable
fun MoviePlaceHolder(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.placeholder),
        contentDescription = null,
    )
}