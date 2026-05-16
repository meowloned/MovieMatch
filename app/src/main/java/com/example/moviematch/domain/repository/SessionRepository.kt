package com.example.moviematch.domain.repository

import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.model.MatchSession
import com.google.firebase.firestore.ListenerRegistration

interface SessionRepository {
    suspend fun getOrCreateSession( currentUserId: String, friendId: String): MatchSession
    suspend fun likeFilm(sessionId: String, userId: String, film: Film): Boolean
    suspend fun checkMatch(sessionId: String, filmId: String, currentUserId: String): Boolean
    suspend fun finishSession(sessionId: String)
    fun listenSession(sessionId: String, onSessionChanged: (MatchSession?)-> Unit): ListenerRegistration
}