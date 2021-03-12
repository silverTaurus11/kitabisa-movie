package com.project.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.R
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.databinding.MovieItemBinding
import com.project.myapplication.ui.adapter.view_holder.MovieItemViewHolder

class MovieListAdapter: AbstractEndlessScrollingPagedListAdapter<MovieEntity, MovieItemViewHolder>(
    object : DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun getMainViewHolder(parent: ViewGroup): MovieItemViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieItemViewHolder(binding)
    }

    override fun getMainViewHolderLayoutResources(): Int = R.layout.movie_item

    override fun onMainBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MovieItemViewHolder){
            holder.bind(getItem(position) as MovieEntity)
        }
    }

}