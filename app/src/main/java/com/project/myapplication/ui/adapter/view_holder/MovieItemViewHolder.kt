package com.project.myapplication.ui.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.databinding.MovieItemBinding
import com.project.myapplication.ui.detail.DetailActivity
import com.project.myapplication.utils.MyHelpers
import com.project.myapplication.utils.MyHelpers.setImageUrl

class MovieItemViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun <T>bind(movieItem: T){
        when(movieItem){
            is MovieEntity -> generateView(movieItem.id, movieItem.name, movieItem.description,
                    movieItem.releaseDate, movieItem.posterPath)
            is RoomMovieEntity -> generateView(movieItem.id, movieItem.name, movieItem.description,
                    movieItem.releaseDate, movieItem.posterPath)
        }
    }

    private fun generateView(movieId: Int?, name: String?, description: String?, releaseDate: String?, url: String?){
        binding.titleTextView.text = name
        binding.descriptionTextView.text = description
        binding.releaseDateTextView.text = releaseDate
        binding.movieImageView.setImageUrl(MyHelpers.getFullMovieImage(url, itemView.context.resources))
        itemView.apply {
            setOnClickListener {
                DetailActivity.startDetailActivity(movieId, context)
            }
        }
    }
}