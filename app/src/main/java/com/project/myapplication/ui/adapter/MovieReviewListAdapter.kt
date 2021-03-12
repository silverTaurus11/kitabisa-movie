package com.project.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.R
import com.project.myapplication.data.source.remote.model.ReviewEntity
import com.project.myapplication.databinding.MovieReviewItemBinding
import com.project.myapplication.ui.adapter.view_holder.MovieReviewItemViewHolder

class MovieReviewListAdapter: AbstractEndlessScrollingPagedListAdapter<ReviewEntity, MovieReviewItemViewHolder>(
    object : DiffUtil.ItemCallback<ReviewEntity>(){
        override fun areItemsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun getMainViewHolder(parent: ViewGroup): MovieReviewItemViewHolder {
        val binding = MovieReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieReviewItemViewHolder(binding)
    }

    override fun getMainViewHolderLayoutResources(): Int = R.layout.movie_review_item

    override fun onMainBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MovieReviewItemViewHolder){
            holder.bind(getItem(position) as ReviewEntity)
        }
    }

}