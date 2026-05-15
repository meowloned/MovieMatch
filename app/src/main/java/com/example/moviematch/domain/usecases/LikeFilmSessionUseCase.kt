package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.repository.SessionRepository

class LikeFilmSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(sessionId: String, userId: String, film: Film):Boolean{
     return sessionRepository.likeFilm(sessionId, userId, film)
    }
}