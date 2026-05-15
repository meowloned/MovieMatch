package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.MatchSession
import com.example.moviematch.domain.repository.SessionRepository

class GetOrCreateSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(currentUserId: String, friendId: String): MatchSession{
        return sessionRepository.getOrCreateSession(currentUserId, friendId)
    }
}