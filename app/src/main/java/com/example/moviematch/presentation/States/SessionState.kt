package com.example.moviematch.presentation.States

data class SessionState (
    val curSessionId: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isMatched: Boolean = false,
    val matchedFilmId: String? = null
)