package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.AuthRepository

class GetCurrentIdUseCase(private  val authRepository: AuthRepository) {
    operator fun invoke():String? {
        return authRepository.getCurrentId()
    }
}