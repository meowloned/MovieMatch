package com.example.moviematch.data.mappers

import com.example.moviematch.data.model.FilmDto
import com.example.moviematch.domain.model.Film

fun FilmDto.filmToDomain(): Film {
    return Film(
        id =  id,
        title = title,
        description_1 = description_1,
        description_2 = description_2,
        year = year,
        rating = rating,
        duration = duration,
        country = country,
        genre = genre,
        posterName = posterName
    )
}