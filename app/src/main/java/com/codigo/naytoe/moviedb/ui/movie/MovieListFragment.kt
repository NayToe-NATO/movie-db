package com.codigo.naytoe.moviedb.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.codigo.naytoe.moviedb.R
import com.codigo.naytoe.moviedb.data.model.Movie
import com.codigo.naytoe.moviedb.databinding.FragmentMovieBinding
import com.codigo.naytoe.moviedb.network.Resource
import com.codigo.naytoe.moviedb.ui.movie.adapter.MovieListAdapter
import com.codigo.naytoe.moviedb.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(), MovieListAdapter.MovieItemListener {

    private var binding: FragmentMovieBinding by autoCleared()
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = MovieListAdapter(this)
        binding.rvUpcoming.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.pbUpcoming.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    binding.pbUpcoming.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onClickedMovie(id: Int) {
        findNavController().navigate(
            R.id.action_movieListFragment_to_movieDetailFragment,
            bundleOf("id" to id)
        )
    }

    override fun onClickedFavourite(movie: Movie) {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.updateMovie(movie)
        }
    }
}