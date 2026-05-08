package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.model.FavouriteFilm
import com.example.moviematch.domain.repository.FavouriteFilmsRepository

class GetFavsUseCase(private val favouriteFilmsRepository: FavouriteFilmsRepository){
    suspend operator fun invoke(userId: String): List<FavouriteFilm> {
        return favouriteFilmsRepository.getfavs(userId)
    }
}