package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.repository.FavouriteFilmsRepository
import com.example.moviematch.domain.repository.FilmsRepository

class GetFavouriteFullFilmsUseCase(private val favouriteFilmsRepository: FavouriteFilmsRepository, private val filmsRepository: FilmsRepository) {
    suspend operator fun invoke(userId: String): List<Film>{
        val favourites = favouriteFilmsRepository.getfavs(userId)
        val films = filmsRepository.getFilms()
        val favouriteIds = favourites.map { it.filmId }.toSet()
        return films.filter { it.id in favouriteIds }
    }
}