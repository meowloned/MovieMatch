package com.example.moviematch.presentation.States

import com.example.moviematch.domain.model.Film

data class FilmsState(
    val films: List<Film> = emptyList(),
    val currentIndex: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)