package com.example.moviejetpack.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert
    suspend fun insertAll(vararg movie: Movie): List<Long>

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}