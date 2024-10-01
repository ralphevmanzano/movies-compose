package com.ralphevmanzano.moviescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.navigation.MovieScreen
import com.ralphevmanzano.moviescompose.navigation.TopLevelRoute
import com.ralphevmanzano.moviescompose.ui.components.MoviesAppBar
import com.ralphevmanzano.moviescompose.ui.details.DetailsScreen
import com.ralphevmanzano.moviescompose.ui.favorites.FavoritesScreen
import com.ralphevmanzano.moviescompose.ui.home.HomeScreen
import com.ralphevmanzano.moviescompose.ui.search.SearchScreen
import com.ralphevmanzano.moviescompose.ui.theme.MoviesComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val routes = listOf(
            TopLevelRoute("Home", MovieScreen.Home, Icons.Filled.Home),
            TopLevelRoute("Search", MovieScreen.Search, Icons.Filled.Search),
            TopLevelRoute("Favorites", MovieScreen.Favorites, Icons.Filled.Star)
        )

        setContent {

            MoviesComposeTheme {
                val navController = rememberNavController()
                Scaffold (
                    topBar = {
                        MoviesAppBar("Movies")
                    },
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            routes.forEach { route ->
                                BottomNavigationItem(
                                    icon = { Icon(route.icon, contentDescription = route.name) },
                                    label = { Text(route.name) },
                                    selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true,
                                    onClick = {
                                       navController.navigate(route.route) {
                                           popUpTo(navController.graph.startDestinationId) {
                                               saveState = true
                                           }
                                           launchSingleTop = true
                                           restoreState = true
                                       }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MovieScreen.Home,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<MovieScreen.Home> {
                            HomeScreen(
                                onMovieClicked = {
                                    navController.navigate(MovieScreen.Details(it))
                                }
                            )
                        }
                        composable<MovieScreen.Details> {
                            DetailsScreen()
                        }
                        composable<MovieScreen.Search> {
                            SearchScreen()
                        }
                        composable<MovieScreen.Favorites> {
                            FavoritesScreen()
                        }
                    }
                }
            }
        }
    }
}