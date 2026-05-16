package com.example.moviematch.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.usecases.AddFavUseCase
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.GetFilmsUseCase
import com.example.moviematch.presentation.States.FilmsState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.Int

class FilmsViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val addFavUseCase: AddFavUseCase,
    private val getCurrentIdUseCase: GetCurrentIdUseCase): ViewModel() {
    var state by mutableStateOf(FilmsState())
        private set
    private var loadJob: Job? = null
    var selectedId by mutableStateOf<String?>(null)

    fun selectFriend(friendId: String){
        selectedId = friendId
    }
    fun getFilmById(filmId: String): Film? {
        return state.films.firstOrNull { film ->
            film.id == filmId
        }
    }

    fun selectOnMe(){
        selectedId = null
    }

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
        val userId = getCurrentIdUseCase()
        val film = getCurFilm()
        if (userId != null && film != null) {
            viewModelScope.launch {
                state = try {
                    addFavUseCase(userId, film.id)
                    state
                } catch (e: Exception) {
                    state.copy(errorMessage = e.message ?: "Не удалось добавить в избранное")
                }
            }
        }
        nextFilm()
    }

    fun dislikeFilm(){
        nextFilm()
    }

    fun searchById(filmid: String): Film? {
        val film = state.films.find { film -> film.id == filmid }
        return film
    }

}