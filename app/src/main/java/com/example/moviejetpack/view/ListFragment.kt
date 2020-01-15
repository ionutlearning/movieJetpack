package com.example.moviejetpack.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.moviejetpack.R
import com.example.moviejetpack.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val movieAdapter = MovieAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.fetchMovies()

        moviesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }

        refreshLayout.setOnRefreshListener {
            moviesList.visibility = GONE
            errorMessage.visibility = GONE
            progressBar.visibility = VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movies.observe(this, Observer { list ->
            moviesList.visibility = VISIBLE
            movieAdapter.updateMovieList(list)
        })

        viewModel.loadError.observe(this, Observer {
            errorMessage.visibility = if (it) VISIBLE else GONE
        })

        viewModel.loading.observe(this, Observer {
            progressBar.visibility = if (it) VISIBLE else GONE
            if (it) {
                errorMessage.visibility = GONE
                moviesList.visibility = GONE
            }
        })
    }
}
