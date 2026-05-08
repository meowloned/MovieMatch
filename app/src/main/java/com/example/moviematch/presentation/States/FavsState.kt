package com.example.moviematch.presentation.States

import com.example.moviematch.domain.model.FavouriteFilm

data class FavsState (
    val favs: List<FavouriteFilm> = emptyList(),
    val isLoading: Boolean  = false,
    val errorMessage: String? = null
)
