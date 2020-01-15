package com.example.moviejetpack.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moviejetpack.R
import com.example.moviejetpack.databinding.MovieItemBinding
import com.example.moviejetpack.model.Movie

class MovieAdapter(private val moviesList: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    fun updateMovieList(newMoviesList: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(newMoviesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<MovieItemBinding>(inflater, R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.view.movie = movie
        holder.view.movieContainer.setOnClickListener {
            val action = ListFragmentDirections.actionDetailFragment()
            action.movieUuid = movie.uuid
            Navigation.findNavController(it).navigate(action)
        }
    }

    class MovieViewHolder(var view: MovieItemBinding) : RecyclerView.ViewHolder(view.root)
}