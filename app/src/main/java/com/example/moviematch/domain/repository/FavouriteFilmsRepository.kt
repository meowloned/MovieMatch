package com.example.moviematch.domain.repository

import com.example.moviematch.domain.model.FavouriteFilm

interface FavouriteFilmsRepository {
    suspend fun getfavs(userId: String):List<FavouriteFilm>
    suspend fun addFav(userId: String, filmId: String)
    suspend fun removeFav(userId: String, filmId: String)
}