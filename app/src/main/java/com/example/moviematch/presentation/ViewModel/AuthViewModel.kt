package com.example.moviematch.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviematch.domain.usecases.GetCurrentIdUseCase
import com.example.moviematch.domain.usecases.LoginUseCase
import com.example.moviematch.domain.usecases.LogoutUseCase
import com.example.moviematch.domain.usecases.RegisterUseCase
import com.example.moviematch.presentation.States.AuthState
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getCurrentIdUseCase: GetCurrentIdUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {
    var state by mutableStateOf(AuthState())
        private set


    fun checkCurrentUser(){
        val curUId = getCurrentIdUseCase()
        if (curUId != null){
            state = state.copy(isLoggedIn = true)
        }
        else{
            state = state.copy(isLoggedIn = false)
        }
    }


    fun onEmailChange(newEmail: String) {
        state = state.copy(email = newEmail)
    }


    fun onPasswordChange(newPassword: String) {
        state = state.copy(password = newPassword)
    }


    fun login(){
        state = state.copy(isLoading = true, errorMessage = "")
        viewModelScope.launch {
            val result = loginUseCase(state.email, state.password)
            if (result.isSuccess){
                state = state.copy(isLoading = false, isLoggedIn = true)
            }
            else{
                state = state.copy(isLoading = false, errorMessage = "Неверное имя пользователя/пароль")
            }
        }
    }


    fun register(){
        state = state.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            val result = registerUseCase(state.email, state.password)
            if (result.isSuccess){
                state = state.copy(isLoading = false, isLoggedIn = true)
            }
            else{
                state = state.copy(isLoading = false, errorMessage = "Пользователь с таким email уже есть")
            }
        }
    }

    fun logout(){
        state = state.copy(isLoading = true, errorMessage = "")
        logoutUseCase()
        state = state.copy(isLoading = false, isLoggedIn = false)
    }
}