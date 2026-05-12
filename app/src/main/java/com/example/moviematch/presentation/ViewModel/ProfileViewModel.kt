package com.example.moviematch.presentation.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.moviematch.domain.usecases.GetCurrentEmailUseCase
import com.example.moviematch.domain.usecases.LogoutUseCase
import com.example.moviematch.presentation.States.ProfileState

class ProfileViewModel(
    private val getCurrentEmailUseCase: GetCurrentEmailUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        loadProfile()
    }

    fun loadProfile() {
        state = state.copy(
            email = getCurrentEmailUseCase()
        )
    }

    fun logout() {
        logoutUseCase()
    }
}
