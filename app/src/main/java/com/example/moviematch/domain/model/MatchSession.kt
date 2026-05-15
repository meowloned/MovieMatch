package com.example.moviematch.domain.model

data class MatchSession (
    val sessionId: String = "",
    val firstUserId: String = "",
    val secondUserId: String = "",
    val status: String = "active",
    val matchedFilmId: String? = null
)