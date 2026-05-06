package com.example.moviematch.data.repositoryImpl

import com.example.moviematch.data.local.FilmJsonDataSource
import com.example.moviematch.data.mappers.filmToDomain
import com.example.moviematch.domain.model.Film
import com.example.moviematch.domain.repository.FilmsRepository

class FilmsRepositoryImpl(private val dataSource: FilmJsonDataSource): FilmsRepository {
    private var films: List<Film>? = null

    override suspend fun getFilms(): List<Film> {
        if (films == null){
            films = dataSource.getFilms().map{it.filmToDomain()}
        }
        return films!!
    }

}