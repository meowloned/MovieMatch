package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.MatchSession
import com.example.moviematch.domain.repository.SessionRepository
import com.google.firebase.firestore.ListenerRegistration

class SessionListenerUseCase(private val sessionRepository: SessionRepository) {
    operator fun invoke(sessionId: String, onSessionChanged:(MatchSession?) -> Unit): ListenerRegistration{
        return sessionRepository.listenSession(sessionId, onSessionChanged)
    }
}