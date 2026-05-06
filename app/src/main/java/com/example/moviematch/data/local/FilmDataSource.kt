package com.example.moviematch.data.local

import android.content.Context
import com.example.moviematch.data.model.FilmDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FilmJsonDataSource(private val context: Context) {
    private val gson = Gson()

    fun getFilms(): List<FilmDto> {
        val json = context.assets.open("films.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<FilmDto>>() {}.type
        return gson.fromJson(json, type)
    }
}