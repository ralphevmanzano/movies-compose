package com.ralphevmanzano.moviescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ralphevmanzano.moviescompose.navigation.MovieScreen
import com.ralphevmanzano.moviescompose.navigation.TopLevelRoute
import com.ralphevmanzano.moviescompose.ui.components.MoviesAppBar
import com.ralphevmanzano.moviescompose.ui.details.DetailsScreen
import com.ralphevmanzano.moviescompose.ui.favorites.MyListScreen
import com.ralphevmanzano.moviescompose.ui.home.HomeScreen
import com.ralphevmanzano.moviescompose.ui.search.SearchScreen
import com.ralphevmanzano.moviescompose.ui.theme.MoviesComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val routes = listOf(
            TopLevelRoute("Home", MovieScreen.Home, Icons.Filled.Home),
            TopLevelRoute("Search", MovieScreen.Search, Icons.Filled.Search),
            TopLevelRoute("My List", MovieScreen.MyList, Icons.Filled.Add)
        )

        setContent {
            MoviesComposeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val showBottomAndAppBar = !(currentDestination?.hasRoute(MovieScreen.Details::class) ?: true)

                Scaffold (
                    topBar = {
                        if (showBottomAndAppBar) {
                            MoviesAppBar("Movies")
                        }
                    },
                    bottomBar = {
                        if (showBottomAndAppBar) {
                            NavigationBar {
                                routes.forEach { route ->
                                    NavigationBarItem(
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
                            DetailsScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable<MovieScreen.Search> {
                            SearchScreen()
                        }
                        composable<MovieScreen.MyList> {
                            MyListScreen()
                        }
                    }
                }
            }
        }
    }
}