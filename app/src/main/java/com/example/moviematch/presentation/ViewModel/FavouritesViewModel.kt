package com.example.moviematch.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.GetFavsUseCase
import com.example.moviematch.domain.usecases.RemoveFavUseCase
import com.example.moviematch.presentation.States.FavsState
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val getFavsUseCase: GetFavsUseCase,
    private val removeFavUseCase: RemoveFavUseCase,
    private val getCurrentIdUseCase: GetCurrentIdUseCase
): ViewModel(){
    var state by mutableStateOf(FavsState())
    private set

    init{
        loadFavs()
    }
    fun loadFavs(){
        val userId = getCurrentIdUseCase()
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            try{
                if (userId != null) {
                    state = state.copy(isLoading = false, favs = getFavsUseCase(userId))
                }
                else{
                    state = state.copy(isLoading = false, errorMessage = "Ошибка загрузки избранного")
                }
            }
            catch (e: Exception){
                state = state.copy(isLoading = false, errorMessage = "Ошибка загрузки избранного")

            }
        }
    }

    fun removeFav(filmId: String){
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            try{
                if (userId != null) {
                    removeFavUseCase(userId, filmId)
                    state = state.copy(isLoading = false, favs = getFavsUseCase(userId))
                }
                else{
                    state = state.copy(isLoading = false, errorMessage = "Ошибка загрузки избранного")
                }
            }
            catch (e: Exception){
                state = state.copy(isLoading = false, errorMessage = "Ошибка загрузки избранного")

            }
        }
    }
}