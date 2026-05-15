package com.example.moviematch.data.repositoryImpl

import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.model.MatchSession
import com.example.moviematch.domain.model.SessionLike
import com.example.moviematch.domain.repository.SessionRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SessionRepositoryImpl: SessionRepository {
    private val firestore = FirebaseFirestore.getInstance()
    override suspend fun checkMatch(sessionId: String, filmId: String, currentUserId: String): Boolean {
        val likes = firestore
            .collection("sessions")
            .document(sessionId)
            .collection("likes")
            .whereEqualTo("filmId",  filmId)
            .get()
            .await()
        val otherLike = likes.firstOrNull { doc ->
            doc.getString("userId") != currentUserId
        }
        if (otherLike != null){
            firestore
                .collection("sessions")
                .document(sessionId)
                .update(
                    mapOf(
                        "matchedFilmId" to filmId,
                        "status" to "matched"
                    )
                )
                .await()
            return true
        }
        return false
    }


    override suspend fun finishSession(sessionId: String) {
        firestore
            .collection("sessions")
            .document(sessionId)
            .update("status", "finished")
            .await()
    }

    override suspend fun getOrCreateSession(currentUserId: String, friendId: String): MatchSession {
        val firstVariant = firestore.collection("sessions")
            .whereEqualTo("firstUserId", currentUserId)
            .whereEqualTo("secondUserId", friendId)
            .whereEqualTo("status", "active")
            .get()
            .await()
        val firstSession= firstVariant.firstOrNull()
        val secondVariant = firestore.collection("sessions")
            .whereEqualTo("firstUserId", friendId)
            .whereEqualTo("secondUserId", currentUserId)
            .whereEqualTo("status", "active")
            .get()
            .await()
        val secondSession =secondVariant.firstOrNull()
        val existingSession = firstSession ?: secondSession
        if (existingSession != null){
            val sessionId = existingSession.getString("sessionId") ?: ""
            val firstUserId = existingSession.getString("firstUserId") ?:""
            val secondUserId = existingSession.getString("secondUserId") ?:""
            val status = existingSession.getString("status") ?:""
            val matchedFilmId = existingSession.getString("matchedFilmId")
            return MatchSession(
                sessionId,
                firstUserId,
                secondUserId,
                status,
                matchedFilmId
            )
        }
        val sessionDoc = firestore.collection("sessions").document()
        val sessionId = sessionDoc.id
        val session = MatchSession(
            sessionId = sessionId,
            firstUserId = currentUserId,
            secondUserId = friendId,
            status = "active",
            matchedFilmId = null,
        )
        sessionDoc.set(session).await()
        return session
    }

    override suspend fun likeFilm(sessionId: String, userId: String, film: Film): Boolean {
        val sessionDoc = firestore
            .collection("sessions")
            .document(sessionId)
            .collection("likes")
            .document("${userId}_${film.id}")
        val like = SessionLike(
            userId,
            film.id,
            film.title,
            film.posterName,
        )
        sessionDoc.set(like).await()
        return (checkMatch(sessionId, film.id, userId))
    }
}