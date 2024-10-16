package com.ralphevmanzano.moviescompose.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ralphevmanzano.moviescompose.designsystem.theme.LightBlue
import com.ralphevmanzano.moviescompose.designsystem.theme.MoviesComposeTheme


@Composable
fun GenreChip(
    modifier: Modifier = Modifier,
    genreName: String,
    shape: Shape = SuggestionChipDefaults.shape,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    SuggestionChip(
        enabled = false,
        label = {
            Text(text = genreName, style = androidx.compose.material3.MaterialTheme.typography.bodySmall, color = LightBlue)
        },
        onClick = { /* Do nothing*/ },
        border = BorderStroke(width = 1.dp, color = LightBlue),
        colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.Transparent),
        modifier = modifier,
        interactionSource = interactionSource,
        shape = shape,

    )
}

@Preview
@Composable
private fun GenreChipPreview() {
    MoviesComposeTheme {
        GenreChip(genreName = "Action")
    }
}