package com.project.myapplication.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myapplication.R
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.local.model.RoomMovieEntity
import com.project.myapplication.databinding.ActivityDetailBinding
import com.project.myapplication.ui.adapter.MovieReviewListAdapter
import com.project.myapplication.utils.EspressoIdlingResource
import com.project.myapplication.utils.MyHelpers
import com.project.myapplication.utils.MyHelpers.setImageUrl
import com.project.myapplication.utils.MyHelpers.toScrollEnabled
import com.project.myapplication.utils.MyHelpers.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity: AppCompatActivity() {

    companion object{
        private const val MOVIE_ID_KEY = "MovieIdKey"
        fun startDetailActivity(id: Int?, context: Context){
            if(id != null){
                context.startActivity(Intent(context, DetailActivity::class.java).apply {
                    putExtra(MOVIE_ID_KEY, id)
                })
            }
        }
    }

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val movieReviewListAdapter by lazy { MovieReviewListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getMovieDetail()
        binding.loadingAndErrorLayout.retryButton.setOnClickListener {
            getMovieDetail()
        }
    }

    private fun initRecyclerView(){
        binding.reviewRecycler.adapter = movieReviewListAdapter
        binding.reviewRecycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
    }

    private fun getMovieDetail(){
        EspressoIdlingResource.increment() //for instrumental test
        val id = getMovieId()
        detailViewModel.getMovieDetail(id).observe(this, {
            binding.loadingAndErrorLayout.progressBar.toVisible(it is Resource.Loading)
            binding.loadingAndErrorLayout.errorTextView.apply{
                toVisible(it is Resource.Error)
                text = it.message
            }
            binding.loadingAndErrorLayout.retryButton.toVisible(it is Resource.Error)
            binding.reviewHeaderTextView.toVisible(it is Resource.Success)
            binding.movieCard.toVisible(it is Resource.Success)
            it.data?.also { movie ->
                generateDetailMovieView(movie)
                getMovieReviews()
            }
        })
    }

    private fun generateDetailMovieView(movie: RoomMovieEntity){
        binding.movieImageView.setImageUrl(MyHelpers.getFullMovieImage(movie.posterPath, resources))
        binding.titleTextView.text = movie.name
        binding.releaseDateTextView.text = movie.releaseDate
        binding.descriptionTextView.text = movie.description
        binding.likeButton.apply{
            setImageDrawable(if(movie.isFavorite){
                ContextCompat.getDrawable(context, R.drawable.ic_favorite)
            } else{
                ContextCompat.getDrawable(context, R.drawable.ic_unfavorite)
            })
            setOnClickListener {
                movie.id?.apply {
                    detailViewModel.updateFavoriteStatus(this, !movie.isFavorite)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMovieReviews(){
        detailViewModel.getMovieReviews(getMovieId()).observe(this, {
            movieReviewListAdapter.setNetworkState(it)
            when(it){
                is Resource.Success -> {
                    movieReviewListAdapter.submitList(it.data)

                    //Task was done
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                is Resource.Empty -> {
                    binding.appbarDetail.toScrollEnabled(false)
                    binding.reviewRecycler.toScrollEnabled(false)
                }
                else -> return@observe
            }
        })
    }

    private fun getMovieId() = intent.getIntExtra(MOVIE_ID_KEY, 0)


}