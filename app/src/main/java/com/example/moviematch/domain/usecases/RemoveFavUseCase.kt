package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.FavouriteFilmsRepository

class RemoveFavUseCase(private val favouriteFilmsRepository: FavouriteFilmsRepository) {
    suspend operator fun invoke(userId: String, filmId: String){
        favouriteFilmsRepository.removeFav(userId, filmId)
    }
}