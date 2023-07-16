package com.example.imdb_api

class FilmResponse(
    val searchType: String,
    val expression: String,
    val results: List<Film>
    )