package com.project.myapplication.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myapplication.databinding.ActivityFavoriteBinding
import com.project.myapplication.ui.adapter.FavoriteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private val favoriteListAdapter by lazy { FavoriteListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getFavoriteMovies()
    }

    private fun initRecyclerView(){
        binding.favoriteRecycler.adapter = favoriteListAdapter
        binding.favoriteRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
    }

    private fun getFavoriteMovies(){
        favoriteViewModel.getFavoriteMovies().observe(this, {
            favoriteListAdapter.submitList(it)
        })
    }

}