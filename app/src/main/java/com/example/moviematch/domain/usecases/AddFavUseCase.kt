package com.example.moviematch.domain.usecases

import com.example.moviematch.domain.repository.FavouriteFilmsRepository

class AddFavUseCase(private val favouriteFilmsRepository: FavouriteFilmsRepository) {
    suspend operator fun invoke(userId: String, filmId: String){
        favouriteFilmsRepository.addFav(userId, filmId)
    }
}