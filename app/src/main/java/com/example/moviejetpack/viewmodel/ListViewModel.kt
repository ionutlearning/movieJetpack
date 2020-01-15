package com.example.moviejetpack.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.moviejetpack.model.Movie
import com.example.moviejetpack.model.MovieDatabase
import com.example.moviejetpack.model.MovieApiService
import com.example.moviejetpack.model.MovieResponse
import com.example.moviejetpack.util.SharedPrefsHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    val movies = MutableLiveData<List<Movie>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val movieApiService = MovieApiService()
    private val disposable = CompositeDisposable()

    private val prefsHelper = SharedPrefsHelper(getApplication())
    private val refreshTime = 30 * 1000 * 1000 * 1000L

    fun fetchMovies() {
        val updateTime = prefsHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val movies = MovieDatabase.getDatabase(getApplication()).getDao().getAllMovies()
            movieRetrieved(movies)
            Toast.makeText(getApplication(), "DB", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshBypassCache() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            movieApiService.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieResponse>() {
                    override fun onSuccess(response: MovieResponse) {
                        storeMoviesLocally(response.results)
                        Toast.makeText(getApplication(), "SERVER", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        loadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun movieRetrieved(list: List<Movie>) {
        movies.value = list
        loadError.value = false
        loading.value = false
    }

    private fun storeMoviesLocally(list: List<Movie>) {
        launch {
            val dao = MovieDatabase.getDatabase(getApplication()).getDao()
            dao.deleteAllMovies()
            val result = dao.insertAll(*list.toTypedArray())
            for (i in list.indices) {
                list[i].uuid = result[i].toInt()
            }
            movieRetrieved(list)
        }

        prefsHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}