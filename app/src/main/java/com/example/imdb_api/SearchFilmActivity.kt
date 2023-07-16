package com.example.imdb_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SearchFilmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_film)

        val searchButton = findViewById<Button>(R.id.searchButton)
        val searchField = findViewById<TextView>(R.id.searchField)


    }
}