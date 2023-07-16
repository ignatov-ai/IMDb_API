package com.example.imdb_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFilmActivity : AppCompatActivity() {

    private val imdbBaseUrl = "https://imdb-api.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(imdbBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApi::class.java)


    private lateinit var placeholderMessage: TextView
    private lateinit var filmListView: RecyclerView
    private lateinit var searchButton: Button
    private lateinit var searchField: EditText

    private val films = ArrayList<Film>()

    private val adapter = FilmAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_film)

        placeholderMessage = findViewById(R.id.placeholderMessage)

        searchButton = findViewById(R.id.searchButton)
        searchField = findViewById(R.id.searchField)

        filmListView = findViewById(R.id.trackList)
        filmListView.layoutManager = LinearLayoutManager(this)

        adapter.films = films

        filmListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        filmListView.adapter = adapter

        searchButton.setOnClickListener {
            if (searchField.text.isNotEmpty()) {
                imdbService.findMovie(searchField.text.toString()).enqueue(object: Callback<FilmResponse> {
                    override fun onResponse(call: Call<FilmResponse>, response: Response<FilmResponse>
                    ) {
                        if (response.code() == 200) {
                            films.clear()
                            if (response.body()?.results?.isNotEmpty() == true) {
                                films.addAll(response.body()?.results!!)
                                adapter.notifyDataSetChanged()
                            }
                            if (films.isEmpty()) {
                                showMessage(getString(R.string.nothing_found), "")
                            } else {
                                showMessage("", "")
                            }
                        } else {
                            showMessage(getString(R.string.something_went_wrong), response.code().toString())
                        }
                    }

                    override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                        showMessage(getString(R.string.something_went_wrong), t.message.toString())
                    }

                })
            }
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            films.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }
}