package com.example.moviejetpack.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejetpack.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    val options = RequestOptions()
        .placeholder(getProgressDrawable(view.context))
        .error(R.mipmap.ic_error)
    Glide.with(view.context)
        .setDefaultRequestOptions(options)
        .load("https://image.tmdb.org/t/p/w185$url")
        .into(view)
}
