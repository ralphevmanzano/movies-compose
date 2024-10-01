@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ralphevmanzano.moviescompose.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.moviescompose.data.repository.details.DetailsRepository
import com.ralphevmanzano.moviescompose.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val movieId: StateFlow<Int?> = savedStateHandle.getStateFlow("id", null)
    val movieDetails: StateFlow<Movie?> = movieId.filterNotNull().flatMapLatest { id ->
        detailsRepository.getMovieDetails(
            id = id,
            onStart = { _uiState.value = DetailsUiState.Loading },
            onError = { _uiState.value = DetailsUiState.Error(it) },
            onComplete = { _uiState.value = DetailsUiState.Idle }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
}

sealed interface DetailsUiState {
    data object Idle: DetailsUiState
    data object Loading: DetailsUiState
    data class Error(val message: String?): DetailsUiState
}
