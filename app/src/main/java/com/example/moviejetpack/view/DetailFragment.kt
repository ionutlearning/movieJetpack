package com.example.moviejetpack.view


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviejetpack.R
import com.example.moviejetpack.databinding.FragmentDetailBinding
import com.example.moviejetpack.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail,
            container, false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var uuid = 0
        arguments?.let {
            uuid = DetailFragmentArgs.fromBundle(it).movieUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetchMovie(uuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.detail.observe(this, Observer { movie ->
            dataBinding.movie = movie
            dataBinding.clickListener = FavoriteClicked(context)
        })
    }

    class FavoriteClicked(val context: Context?) : FavoriteClickListener {
        override fun onFavoriteClicked(id: Int) {
            Toast.makeText(context, "id: $id", Toast.LENGTH_SHORT).show()
        }
    }
}
