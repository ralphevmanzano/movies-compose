package com.ralphevmanzano.moviescompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.moviescompose.data.repository.home.HomeRepository
import com.ralphevmanzano.moviescompose.domain.model.Category
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.domain.model.MoviesWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _allMovies = MutableStateFlow(MoviesWithCategory(Movie()))
    val allMovies = _allMovies.asStateFlow()

    private var nowPlayingPage = 1
    private var upcomingPage = 1
    private var popularPage = 1
    private var topRatedPage = 1

    fun fetchAllMovies() {
        viewModelScope.launch {
            homeRepository.getAllMovies(
                onStart = { _uiState.value = HomeUiState.Loading(null) },
                onError = { _uiState.value = HomeUiState.Error(it) },
                onComplete = { _uiState.value = HomeUiState.Idle }
            ).collect {
                _allMovies.value = it
            }
        }
    }

    fun fetchNextPage(category: Category) {
        if (uiState.value !is HomeUiState.Loading) {
            when (category) {
                Category.NOW_PLAYING -> fetchNowPlaying()
                Category.UPCOMING -> fetchUpcoming()
                Category.POPULAR -> fetchPopular()
                Category.TOP_RATED -> fetchTopRated()
            }
        }
    }

    private fun fetchNowPlaying() {
        viewModelScope.launch {
            if (_uiState.value != HomeUiState.Loading(Category.NOW_PLAYING)) {
                nowPlayingPage++
                homeRepository.getNowPlaying(
                    page = nowPlayingPage,
                    onStart = { _uiState.value = HomeUiState.Loading(Category.NOW_PLAYING) },
                    onComplete = { _uiState.value = HomeUiState.Idle },
                    onError = { _uiState.value = HomeUiState.Error(it) }
                ).collect { newPageMovies ->
                    _allMovies.update { allMovies ->
                        val currentMovies = allMovies.nowPlaying
                        allMovies.copy(nowPlaying = (currentMovies + newPageMovies).distinctBy { it.id })
                    }
                }
            }
        }
    }

    private fun fetchUpcoming() {
        viewModelScope.launch {
            if (_uiState.value != HomeUiState.Loading(Category.UPCOMING)) {
                upcomingPage++
                homeRepository.getUpcoming(
                    page = upcomingPage,
                    onStart = { _uiState.value = HomeUiState.Loading(Category.UPCOMING) },
                    onComplete = { _uiState.value = HomeUiState.Idle },
                    onError = { _uiState.value = HomeUiState.Error(it) }
                ).collect { newPageMovies ->
                    _allMovies.update { allMovies ->
                        val currentMovies = allMovies.upcoming
                        allMovies.copy(upcoming = (currentMovies + newPageMovies).distinctBy { it.id })
                    }
                }
            }
        }
    }

    private fun fetchPopular() {
        viewModelScope.launch {
            if (_uiState.value != HomeUiState.Loading(Category.POPULAR)) {
                popularPage++
                homeRepository.getPopular(
                    page = popularPage,
                    onStart = { _uiState.value = HomeUiState.Loading(Category.POPULAR) },
                    onComplete = { _uiState.value = HomeUiState.Idle },
                    onError = { _uiState.value = HomeUiState.Error(it) }
                ).collect { newPageMovies ->
                    _allMovies.update { allMovies ->
                        val currentMovies = allMovies.popular
                        allMovies.copy(popular = (currentMovies + newPageMovies).distinctBy { it.id })
                    }
                }
            }
        }
    }

    private fun fetchTopRated() {
        viewModelScope.launch {
            if (_uiState.value != HomeUiState.Loading(Category.TOP_RATED)) {
                topRatedPage++
                homeRepository.getTopRated(
                    page = topRatedPage,
                    onStart = { _uiState.value = HomeUiState.Loading(Category.TOP_RATED) },
                    onComplete = { _uiState.value = HomeUiState.Idle },
                    onError = { _uiState.value = HomeUiState.Error(it) }
                ).collect { newPageMovies ->
                    _allMovies.update { allMovies ->
                        val currentMovies = allMovies.topRated
                        allMovies.copy(topRated = (currentMovies + newPageMovies).distinctBy { it.id })
                    }
                }
            }
        }
    }
}

sealed interface HomeUiState {
    data object Idle: HomeUiState
    data class Loading(val category: Category?): HomeUiState
    data class Error(val message: String?): HomeUiState
}

