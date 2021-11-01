package com.codigo.naytoe.moviedb.ui.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codigo.naytoe.moviedb.R
import com.codigo.naytoe.moviedb.data.model.Movie
import com.codigo.naytoe.moviedb.databinding.ItemMovieBinding
import com.codigo.naytoe.moviedb.util.AppConst

class MovieListAdapter(private val listener: MovieItemListener) : RecyclerView.Adapter<MovieViewHolder>() {

    interface MovieItemListener {
        fun onClickedMovie(id: Int)
        fun onClickedFavourite(movie: Movie)
    }

    private val items = ArrayList<Movie>()

    fun setItems(items: ArrayList<Movie>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(items[position])
}

class MovieViewHolder(private val itemBinding: ItemMovieBinding, private val listener: MovieListAdapter.MovieItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var movie: Movie

    init {
        itemBinding.root.setOnClickListener(this)
        itemBinding.btnFav.setOnCheckedChangeListener { _, isChecked ->
            movie.favourite = isChecked
            listener.onClickedFavourite(movie)
        }
    }

    fun bind(item: Movie) {
        this.movie = item
        itemBinding.tvName.text = item.title
        itemBinding.tvDate.text = item.releaseDate
        Glide.with(itemBinding.root)
            .load(AppConst.IMAGE_URL + item.posterPath)
            .placeholder(R.drawable.placeholder)
            .into(itemBinding.ivCover)
        itemBinding.btnFav.isChecked = item.favourite
    }

    override fun onClick(v: View?) {
        listener.onClickedMovie(movie.id)
    }
}