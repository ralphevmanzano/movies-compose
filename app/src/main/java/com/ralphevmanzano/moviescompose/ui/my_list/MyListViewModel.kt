package com.ralphevmanzano.moviescompose.ui.my_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphevmanzano.moviescompose.data.repository.my_list.MyListRepository
import com.ralphevmanzano.moviescompose.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    myListRepository: MyListRepository
) : ViewModel() {

    val myList: StateFlow<List<Movie>> = myListRepository.getMyList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

}