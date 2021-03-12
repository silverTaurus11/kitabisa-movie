package com.project.myapplication.ui.adapter.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.data.source.remote.model.ReviewEntity
import com.project.myapplication.databinding.MovieReviewItemBinding
import com.project.myapplication.utils.MyHelpers
import com.project.myapplication.utils.MyHelpers.setTextHtml

class MovieReviewItemViewHolder(private val binding: MovieReviewItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ReviewEntity){
        binding.authorTextView.text = data.author
        binding.contentTextView.setTextHtml(data.content)
        binding.createdDateTextView.text = MyHelpers.printReviewDate(data.createdAt)
    }
}