package com.example.moviematch.data.repositoryImpl

import com.example.moviematch.domain.model.FavouriteFilm
import com.example.moviematch.domain.repository.FavouriteFilmsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavouriteFilmsRepositoryImpl: FavouriteFilmsRepository {
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun getfavs(userId: String): List<FavouriteFilm> {
        val favs = firestore
            .collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .await()

        val list = favs.documents.mapNotNull { document ->
            val filmId = document.getString("filmId") ?: ""
            val userId = document.getString("userId") ?: ""
            val timestamp = document.getLong("timestamp") ?: 0L
            FavouriteFilm(
                filmId = filmId,
                userId = userId,
                timestamp = timestamp
            )
        }
        return list
    }

    override suspend fun addFav(userId: String, filmId: String) {
        val favourites =firestore
            .collection("users")
            .document(userId)
            .collection("favorites")
            .document(filmId)
        val timestamp = System.currentTimeMillis()
        val film  = FavouriteFilm(
            filmId = filmId,
            userId = userId,
            timestamp = timestamp
            )
        favourites.set(film).await()
    }

    override suspend fun removeFav(userId: String, filmId: String) {
        val favourites =firestore
            .collection("users")
            .document(userId)
            .collection("favorites")
            .document(filmId)
            .delete()
            .await()
    }
}