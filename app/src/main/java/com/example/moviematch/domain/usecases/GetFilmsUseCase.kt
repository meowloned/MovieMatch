package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.repository.FilmsRepository

class GetFilmsUseCase(private val repository: FilmsRepository) {
    suspend operator fun invoke():List<Film>{
        return repository.getFilms()
    }
}