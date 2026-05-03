package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(){
        authRepository.logout()
    }
}