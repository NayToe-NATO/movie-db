package com.codigo.naytoe.moviedb.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.codigo.naytoe.moviedb.R
import com.codigo.naytoe.moviedb.data.model.Movie
import com.codigo.naytoe.moviedb.databinding.FragmentMovieDetailBinding
import com.codigo.naytoe.moviedb.network.Resource
import com.codigo.naytoe.moviedb.util.AppConst
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
        setupInputEvents()
    }

    private fun setupObservers() {
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindData(it.data!!)
                    binding.progressBar.visibility = View.GONE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupInputEvents() {
        binding.btnFav.setOnCheckedChangeListener { _, isChecked ->
            movie.favourite = isChecked
            updateMovie()
        }
    }

    private fun updateMovie() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.updateMovie(movie)
        }
    }

    private fun bindData(movie: Movie) {
        this.movie = movie
        binding.tvName.text = movie.title
        binding.tvOverview.text = movie.overview
        Glide.with(binding.root)
            .load(AppConst.IMAGE_URL + movie.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(binding.ivCover)
        binding.btnFav.isCheckable = movie.favourite
    }
}