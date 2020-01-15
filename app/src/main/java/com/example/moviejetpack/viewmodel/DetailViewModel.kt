package com.example.moviejetpack.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.moviejetpack.model.Movie
import com.example.moviejetpack.model.MovieDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val detail = MutableLiveData<Movie>()

    fun fetchMovie(uuid: Int) {
        launch {
            val movie = MovieDatabase.getDatabase(getApplication()).getDao().getMovieById(uuid)
            detail.value = movie
        }
    }
}