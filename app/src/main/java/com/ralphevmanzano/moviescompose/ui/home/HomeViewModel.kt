package com.ralphevmanzano.moviescompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ralphevmanzano.moviescompose.data.repository.home.HomeRepository
import com.ralphevmanzano.moviescompose.data.repository.my_list.MyListRepository
import com.ralphevmanzano.moviescompose.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeRepository: HomeRepository,
    private val myListRepository: MyListRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    val myList: StateFlow<List<Movie>> = myListRepository.getMyList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val nowPlayingPaging = homeRepository.getNowPlayingPaging()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        ).cachedIn(viewModelScope)

    val popularPaging = homeRepository.getPopularPaging()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        ).cachedIn(viewModelScope)

    val topRatedPaging = homeRepository.getTopRatedPaging()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        ).cachedIn(viewModelScope)

    val upcomingPaging = homeRepository.getUpcomingPaging()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PagingData.empty()
        ).cachedIn(viewModelScope)

    fun addToMyList(movie: Movie) {
        viewModelScope.launch {
            if (myList.value.any { it.id == movie.id }) {
                myListRepository.removeFromMyList(movie)
            } else {
                myListRepository.addToMyList(movie)
            }
        }
    }

    fun generateFeaturedMovie(itemSnapshotList: ItemSnapshotList<Movie>) {
        if (_uiState.value.featured == null && itemSnapshotList.isNotEmpty()) {
            val featured = itemSnapshotList.random()
            _uiState.update {
                it.copy(featured = featured)
            }
        }
    }

    fun consumeNowPlayingLoadState(loadState: CombinedLoadStates) {
        when (loadState.append) {
            is LoadState.Error -> {
                _uiState.update {
                    it.copy(
                        error = (loadState.append as LoadState.Error).error.message,
                        isLoadingNowPlaying = false
                    )
                }
            }

            is LoadState.Loading -> {
                _uiState.update {
                    it.copy(isLoadingNowPlaying = true)
                }
            }

            else -> {
                _uiState.update {
                    it.copy(isLoadingNowPlaying = false)
                }
            }
        }
    }

    fun consumePopularLoadState(loadState: CombinedLoadStates) {
        when (loadState.append) {
            is LoadState.Error -> {
                _uiState.update {
                    it.copy(
                        error = (loadState.append as LoadState.Error).error.message,
                        isLoadingPopular = false
                    )
                }
            }

            is LoadState.Loading -> {
                _uiState.update {
                    it.copy(isLoadingPopular = true)
                }
            }

            else -> {
                _uiState.update {
                    it.copy(isLoadingPopular = false)
                }
            }
        }
    }

    fun consumeTopRatedLoadState(loadState: CombinedLoadStates) {
        when (loadState.append) {
            is LoadState.Error -> {
                _uiState.update {
                    it.copy(
                        error = (loadState.append as LoadState.Error).error.message,
                        isLoadingTopRated = false
                    )
                }
            }

            is LoadState.Loading -> {
                _uiState.update {
                    it.copy(isLoadingTopRated = true)
                }
            }

            else -> {
                _uiState.update {
                    it.copy(isLoadingTopRated = false)
                }
            }
        }
    }

    fun consumeUpcomingLoadState(loadState: CombinedLoadStates) {
        when (loadState.append) {
            is LoadState.Error -> {
                _uiState.update {
                    it.copy(
                        error = (loadState.append as LoadState.Error).error.message,
                        isLoadingUpcoming = false
                    )
                }
            }

            is LoadState.Loading -> {
                _uiState.update {
                    it.copy(isLoadingUpcoming = true)
                }
            }

            else -> {
                _uiState.update {
                    it.copy(isLoadingUpcoming = false)
                }
            }
        }
    }
}

data class HomeUiState(
    val featured: Movie? = null,
    val isLoadingNowPlaying: Boolean = false,
    val isLoadingPopular: Boolean = false,
    val isLoadingTopRated: Boolean = false,
    val isLoadingUpcoming: Boolean = false,
    val error: String? = null
)

