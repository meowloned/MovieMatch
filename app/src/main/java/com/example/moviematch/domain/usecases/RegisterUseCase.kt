package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.AuthRepository

class RegisterUseCase(private  val authRepository: AuthRepository) {
    suspend operator fun invoke(email:String, password:String): Result<Unit>{
        return authRepository.register(email, password)
    }
}