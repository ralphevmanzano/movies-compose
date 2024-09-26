package com.ralphevmanzano.moviescompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.moviescompose.data.repository.home.HomeRepository
import com.ralphevmanzano.moviescompose.domain.model.Movie
import com.ralphevmanzano.moviescompose.domain.model.MoviesWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState = _uiState.asStateFlow()

    val allMovies: StateFlow<MoviesWithCategory> = homeRepository.getAllMovies(
        onStart = { _uiState.value = HomeUiState.Loading },
        onError = { _uiState.value = HomeUiState.Error(it) },
        onComplete = { _uiState.value = HomeUiState.Idle }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MoviesWithCategory()
    )
}

sealed interface HomeUiState {
    data object Idle: HomeUiState
    data object Loading: HomeUiState
    data class Error(val message: String?): HomeUiState
}