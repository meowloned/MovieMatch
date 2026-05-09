package com.example.moviematch.presentation.States

import com.example.moviematch.domain.model.User

data class FindState (
    val searchEmail: String = "",
    val foundUser: User? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)