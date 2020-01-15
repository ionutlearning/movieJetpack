package com.example.moviejetpack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var uuid: Int,
    var title: String,
    var overview: String,
    @ColumnInfo(name = "poster")
    @SerializedName("poster_path")
    var posterPath: String,
    @ColumnInfo(name = "votes")
    @SerializedName("vote_average")
    var voteAverage: Double,
    @ColumnInfo(name = "date")
    @SerializedName("release_date")
    var releaseDate: String
)