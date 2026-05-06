package com.example.moviematch.domain.repository

import com.example.moviematch.domain.model.Film

interface FilmsRepository {
    suspend fun getFilms():List<Film>
}