package com.project.myapplication.ui.adapter.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.databinding.LoadmoreStateItemBinding
import com.project.myapplication.utils.MyHelpers.toVisible

class LoadMoreStateViewHolder(private val binding: LoadmoreStateItemBinding): RecyclerView.ViewHolder(binding.root) {
    enum class NetworkState(var message: String?= null){
        STATE_LOADING,
        STATE_SUCCESS,
        STATE_ERROR
    }

    fun bind(state: NetworkState?, retryClickListener: View.OnClickListener?){
        binding.progressBar.toVisible(state == NetworkState.STATE_LOADING)
        binding.retryButton.apply {
            toVisible(state == NetworkState.STATE_ERROR)
            setOnClickListener(retryClickListener)
        }
        binding.errorMsg.toVisible(state == NetworkState.STATE_ERROR)
        binding.errorMsg.text = state?.message
    }

}