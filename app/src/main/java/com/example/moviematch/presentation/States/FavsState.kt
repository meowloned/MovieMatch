package com.example.moviematch.presentation.States

import com.example.moviematch.domain.model.Film

data class FavsState (
    val favs: List<Film> = emptyList(),
    val isLoading: Boolean  = false,
    val errorMessage: String? = null
)
