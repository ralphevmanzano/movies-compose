package com.ralphevmanzano.moviescompose.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.ralphevmanzano.moviescompose.data.repository.search.SearchRepository
import com.ralphevmanzano.moviescompose.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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

    val searchPaging: Flow<PagingData<Movie>> = query.flatMapLatest { q ->
        if (q.isBlank() || q.length < 3) {
            _uiState.value = SearchUiState.Idle
            flowOf(PagingData.empty())
        } else {
            searchRepository.searchMovies(query = q)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    ).cachedIn(viewModelScope)

    fun onSearchChanged(q: String) {
        query.value = q
    }

    fun consumeSearchLoadState(lazyPagingItems: LazyPagingItems<Movie>) {
        val loadState = lazyPagingItems.loadState
        when (loadState.append) {
            is LoadState.Error -> {
                _uiState.value = SearchUiState.Error((loadState.append as LoadState.Error).error.message)
            }

            is LoadState.Loading -> {
                _uiState.value = SearchUiState.Loading
            }

            else -> {
                if (lazyPagingItems.itemCount == 0) {
                    _uiState.value = SearchUiState.NoResults
                } else {
                    _uiState.value = SearchUiState.Idle
                }
            }
        }
    }
}

sealed interface SearchUiState {
    data object Idle: SearchUiState
    data object Loading: SearchUiState
    data object NoResults: SearchUiState
    data class Error(val message: String?): SearchUiState
}
