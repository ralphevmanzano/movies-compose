package com.ralphevmanzano.moviescompose.ui.search

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.moviescompose.data.repository.search.SearchRepository
import com.ralphevmanzano.moviescompose.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val searchFlow: Flow<List<Movie>> = combine(query, page) { query, page ->
        query to page
    }.flatMapLatest { (query, page) ->
        if (query.isBlank() || query.length < 3) {
            _uiState.value = SearchUiState.Idle
            _searchResults.value = emptyList()
            flowOf(emptyList())
        } else {
            searchRepository.searchMovies(
                query = query,
                page = page,
                onStart = { _uiState.value = SearchUiState.Loading },
                onError = { _uiState.value = SearchUiState.Error(it) },
                onComplete = {  }
            ).onEach {
                if (it.isEmpty()) {
                    _uiState.value = SearchUiState.NoResults
                    _searchResults.value = emptyList()
                } else {
                    if (page > 1) {
                        _searchResults.value += it
                    } else {
                        _searchResults.value = it
                    }
                    _uiState.value = SearchUiState.Idle
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            searchFlow.collect()
        }
    }

    fun onSearchChanged(q: String) {
        query.value = q
        page.value = 1
    }

    fun onNextPage() {
        page.value++
    }
}

sealed interface SearchUiState {
    data object Idle: SearchUiState
    data object Loading: SearchUiState
    data object NoResults: SearchUiState
    data class Error(val message: String?): SearchUiState
}
