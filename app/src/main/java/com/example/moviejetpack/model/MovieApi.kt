package com.example.moviejetpack.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    fun getMovies(@Query("api_key") apiKey: String): Single<MovieResponse>

}