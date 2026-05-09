package com.example.moviematch.presentation.States

import com.example.moviematch.domain.model.Request

data class RequestState (
    val requests: List<Request> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)