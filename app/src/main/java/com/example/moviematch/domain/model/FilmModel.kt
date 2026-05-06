package com.example.moviematch.domain.model

data class Film(
    val id: String,
    val title: String,
    val description_1: String,
    val description_2: String,
    val year: Int,
    val rating: Double,
    val duration: String,
    val country: String,
    val genre: String,
    val posterName: String
    )