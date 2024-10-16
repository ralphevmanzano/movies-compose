package com.ralphevmanzano.moviescompose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ralphevmanzano.moviescompose.designsystem.R
import com.ralphevmanzano.moviescompose.designsystem.theme.MoviesComposeTheme

@Composable
fun PoweredBySection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(com.ralphevmanzano.moviescompose.designsystem.R.string.powered_by),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Black,
            fontSize = 10.sp,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Image(
            modifier = Modifier.width(112.dp),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.tmdb),
            contentDescription = stringResource(R.string.tmdb_logo)
        )
    }
}

@Preview
@Composable
private fun PoweredBySectionPreview() {
    MoviesComposeTheme {
        PoweredBySection()
    }
}