package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit>{
        return authRepository.login(email, password)
    }
}