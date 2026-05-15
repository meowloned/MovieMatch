package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.SessionRepository

class FinishSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(sessionId:String){
        sessionRepository.finishSession(sessionId)
    }
}