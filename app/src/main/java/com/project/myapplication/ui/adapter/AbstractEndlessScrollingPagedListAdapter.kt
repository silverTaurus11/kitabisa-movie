package com.project.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.myapplication.R
import com.project.myapplication.data.Resource
import com.project.myapplication.databinding.LoadmoreStateItemBinding
import com.project.myapplication.ui.adapter.view_holder.LoadMoreStateViewHolder

abstract class AbstractEndlessScrollingPagedListAdapter<Entity, MainViewHolderType: RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<Entity>)
    : PagedListAdapter<Entity, RecyclerView.ViewHolder>(diffCallback) {
    private var retryClickListener: View.OnClickListener?= null

    private var networkState: LoadMoreStateViewHolder.NetworkState?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            getMainViewHolderLayoutResources() -> getMainViewHolder(parent)
            R.layout.loadmore_state_item -> {
                val binding = LoadmoreStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadMoreStateViewHolder(binding)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LoadMoreStateViewHolder -> holder.bind(networkState, retryClickListener)
            else -> onMainBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoadMoreActive() && position == itemCount - 1) {
            R.layout.loadmore_state_item
        } else {
            getMainViewHolderLayoutResources()
        }
    }

    fun setNetworkState(resources: Resource<PagedList<Entity>>){
        val previousState = this.networkState
        val hadExtraRow = isLoadMoreActive()
        when(resources){
            is Resource.Loading -> {
                if(resources.isLoadMoreBehaviour){
                    networkState = LoadMoreStateViewHolder.NetworkState.STATE_LOADING
                }
            }
            is Resource.Error -> {
                if(resources.isLoadMoreBehaviour){
                    networkState = LoadMoreStateViewHolder.NetworkState.STATE_ERROR.apply {
                        message = resources.message
                    }
                    retryClickListener = resources.retry
                }
            }
            else -> networkState = LoadMoreStateViewHolder.NetworkState.STATE_SUCCESS
        }
        val hasExtraRow = isLoadMoreActive()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != this.networkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isLoadMoreActive()) 1 else 0
    }

    private fun isLoadMoreActive(): Boolean{
        return networkState != null && networkState != LoadMoreStateViewHolder.NetworkState.STATE_SUCCESS
    }

    abstract fun getMainViewHolder(parent: ViewGroup): MainViewHolderType
    abstract fun getMainViewHolderLayoutResources(): Int
    abstract fun onMainBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

}