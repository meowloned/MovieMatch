package com.example.moviematch.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.usecases.FinishSessionUseCase
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.GetOrCreateSessionUseCase
import com.example.moviematch.domain.usecases.LikeFilmSessionUseCase
import com.example.moviematch.presentation.States.SessionState
import kotlinx.coroutines.launch
import kotlin.String

class SessionViewModel(
    private val getOrCreateSessionUseCase: GetOrCreateSessionUseCase,
    private val finishSessionUseCase: FinishSessionUseCase,
    private val likeFilmSessionUseCase: LikeFilmSessionUseCase,
    private val getCurrentIdUseCase: GetCurrentIdUseCase
): ViewModel() {
    var state by mutableStateOf(SessionState())
    private set
    fun startSession(friendId:String){
        state = state.copy(isLoading = true)
        val userId = getCurrentIdUseCase()
        if (userId != null) {
            viewModelScope.launch {
                try {
                    val session = getOrCreateSessionUseCase(userId, friendId)
                    state = state.copy(
                        isLoading = false,
                        curSessionId = session.sessionId,
                        errorMessage = null,
                        isMatched = false
                    )
                } catch (e: Exception) {
                    state = state.copy(isLoading = false, errorMessage = "Не удалось создать сессию с другом")
                }
            }
        }
        else{
            state = state.copy(isLoading = false, errorMessage = "Не удалось создать сессию с другом")
        }
    }

    fun likeFilm(film: Film){
        state = state.copy(errorMessage = null)
        val userId = getCurrentIdUseCase()
        val sessionId = state.curSessionId
        if (userId != null && sessionId != null) {
            viewModelScope.launch {
                try{
                    if (likeFilmSessionUseCase(sessionId, userId, film)) {
                        state = state.copy(isMatched = true, matchedFilmId = film.id)
                    }
                }
                catch(e: Exception){
                    state = state.copy(errorMessage = "Не удалось лайкнуть фильм")
                }
            }
        }
    }

    fun finishSession(){
        val sessionId = state.curSessionId
        if (sessionId != null){
            viewModelScope.launch {
                try{
                    finishSessionUseCase(sessionId)
                    state = SessionState()
                }
                catch(e: Exception){
                    state = state.copy(errorMessage = "Не удалось закончить сессию")
                }
            }
        }
    }
}