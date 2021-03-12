package com.project.myapplication.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.databinding.MovieItemBinding
import com.project.myapplication.ui.adapter.view_holder.MovieItemViewHolder

class FavoriteListAdapter: PagedListAdapter<RoomMovieEntity, MovieItemViewHolder>(
        object : DiffUtil.ItemCallback<RoomMovieEntity>(){
            override fun areItemsTheSame(oldItem: RoomMovieEntity, newItem: RoomMovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: RoomMovieEntity, newItem: RoomMovieEntity): Boolean {
                return oldItem == newItem
            }

        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position) as RoomMovieEntity)
    }
}