package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.repository.FavouriteFilmsRepository
import com.example.moviematch.domain.repository.FilmsRepository

class GetFilmsNotInFavouritesUseCase(private val filmsRepository:   FilmsRepository,
                                     private val favouritesRepository: FavouriteFilmsRepository) {
    suspend operator fun invoke(userId: String): List<Film> {
        val films = filmsRepository.getFilms()
        val favourites = favouritesRepository.getfavs(userId)
        val favouritesId = favourites.map { it.filmId }.toSet()
        return films.filter { film ->
            film.id !in favouritesId
        }
    }
}