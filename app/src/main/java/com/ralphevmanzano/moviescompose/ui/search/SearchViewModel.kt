package com.ralphevmanzano.moviescompose.ui.search

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.moviescompose.data.repository.search.SearchRepository
import com.ralphevmanzano.moviescompose.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val query = MutableStateFlow("")
    private val page = MutableStateFlow(1)

    val searchResults: StateFlow<List<Movie>> = combine(query, page) { query, page ->
        query to page
    }.flatMapLatest { (query, page) ->
        searchRepository.searchMovies(
            query = query,
            page = page,
            onStart = { _uiState.value = SearchUiState.Loading },
            onError = { _uiState.value = SearchUiState.Error(it) },
            onComplete = { _uiState.value = SearchUiState.Idle }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onSearchChanged(q: String) {
        if (q.isNotEmpty()) {
            query.value = q
            page.value = 1
        }
    }

    fun onNextPage() {
        page.value++
    }
}

sealed interface SearchUiState {
    data object Idle: SearchUiState
    data object Loading: SearchUiState
    data class Error(val message: String?): SearchUiState
}
