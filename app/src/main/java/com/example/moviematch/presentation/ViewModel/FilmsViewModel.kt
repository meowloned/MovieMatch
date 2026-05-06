package com.example.moviematch.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.usecases.GetFilmsUseCase
import com.example.moviematch.presentation.States.FilmsState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FilmsViewModel(private val getFilmsUseCase: GetFilmsUseCase): ViewModel() {
    var state by mutableStateOf(FilmsState())
        private set
    private var loadJob: Job? = null
    fun loadFilms(){
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            state = state.copy(isLoading = true)
            state = try {
                state.copy(films = getFilmsUseCase(), isLoading = false)
            } catch(e: Exception){
                state.copy(errorMessage = "Не удалось загрузить фильм", isLoading = false)
            }
        }
    }

    init{
        loadFilms()
    }

    fun getCurFilm(): Film?{
        return state.films.getOrNull(state.currentIndex)
    }


    fun nextFilm(){
        if (state.currentIndex < state.films.size - 1) {
            state = state.copy(currentIndex = state.currentIndex + 1)
        }
    }

    fun likeFilm(){
        nextFilm()
    }

    fun dislikeFilm(){
        nextFilm()
    }
}