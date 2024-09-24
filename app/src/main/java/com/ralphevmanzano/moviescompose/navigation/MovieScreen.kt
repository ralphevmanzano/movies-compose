package com.ralphevmanzano.moviescompose.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface MovieScreen {
    @Serializable
    data object Home: MovieScreen

    @Serializable
    data class Details(val id: Int): MovieScreen

    @Serializable
    data object Search: MovieScreen

    @Serializable
    data object Favorites: MovieScreen
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)
