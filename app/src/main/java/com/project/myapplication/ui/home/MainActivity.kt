package com.project.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myapplication.R
import com.project.myapplication.data.Resource
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.databinding.ActivityMainBinding
import com.project.myapplication.ui.adapter.MovieListAdapter
import com.project.myapplication.ui.category.CategoryBottomSheetDialog
import com.project.myapplication.ui.favorite.FavoriteActivity
import com.project.myapplication.utils.EspressoIdlingResource
import com.project.myapplication.utils.MyHelpers.toVisible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CategoryBottomSheetDialog.CategoryBottomSheetDialogListener {

    private enum class MovieCategory{
        POPULAR_MOVIES,
        TOP_RATED_MOVIES,
        UPCOMING_MOVIES,
        NEW_PLAYING_MOVIES
    }

    private var selectedMovieCategory = MovieCategory.POPULAR_MOVIES
    private lateinit var binding: ActivityMainBinding
    private val movieListAdapter by lazy { MovieListAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        getPopularMovies()
        initSwipeRefresh()
        initCategoryButton()
    }

    private fun initRecyclerView(){
        binding.movieRecycler.adapter = movieListAdapter
        binding.movieRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
    }

    private fun initSwipeRefresh(){
        binding.swipeRefresh.setOnRefreshListener {
            retryGetMoviesWithLastState()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefresh.isRefreshing = false
            }, 200)
        }
    }

    private fun retryGetMoviesWithLastState(){
        when(selectedMovieCategory){
            MovieCategory.POPULAR_MOVIES -> getPopularMovies()
            MovieCategory.NEW_PLAYING_MOVIES -> getNowPlayingMovies()
            MovieCategory.TOP_RATED_MOVIES -> getTopRatedMovies()
            MovieCategory.UPCOMING_MOVIES -> getUpComingMovies()
        }
    }

    private fun initCategoryButton(){
        binding.categoryTextView.setOnClickListener {
            CategoryBottomSheetDialog.showDialog(supportFragmentManager, this)
        }
    }

    private fun getPopularMovies(){
        EspressoIdlingResource.increment() //for instrumental test
        mainViewModel.getPopularMovies().observe(this, {
            generateView(it)
        })
        selectedMovieCategory = MovieCategory.POPULAR_MOVIES
    }

    private fun getTopRatedMovies(){
        EspressoIdlingResource.increment() //for instrumental test
        mainViewModel.getTopRatedMovies().observe(this, {
            generateView(it)
        })
        selectedMovieCategory = MovieCategory.TOP_RATED_MOVIES
    }

    private fun getNowPlayingMovies(){
        EspressoIdlingResource.increment() //for instrumental test
        mainViewModel.getNowPlaying().observe(this, {
            generateView(it)
        })
        selectedMovieCategory = MovieCategory.NEW_PLAYING_MOVIES
    }

    private fun getUpComingMovies(){
        EspressoIdlingResource.increment() //for instrumental test
        mainViewModel.getUpComingMovies().observe(this, {
            generateView(it)
        })
        selectedMovieCategory = MovieCategory.UPCOMING_MOVIES
    }

    private fun generateView(resource: Resource<PagedList<MovieEntity>>){
        movieListAdapter.setNetworkState(resource)
        binding.loadingAndErrorLayout.progressBar.toVisible(resource is Resource.Loading
                && !resource.isLoadMoreBehaviour)
        binding.movieRecycler.toVisible(resource is Resource.Success
                || (resource is Resource.Loading && resource.isLoadMoreBehaviour)
                || (resource is Resource.Error && resource.isLoadMoreBehaviour))
        binding.loadingAndErrorLayout.errorTextView.toVisible(resource is Resource.Error
                && !resource.isLoadMoreBehaviour)
        binding.loadingAndErrorLayout.retryButton.apply {
            toVisible(resource is Resource.Error
                    && !resource.isLoadMoreBehaviour)
            setOnClickListener {
                retryGetMoviesWithLastState()
            }
        }
        when(resource){
            is Resource.Success -> {
                movieListAdapter.submitList(resource.data)

                //Task was done
                if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                    EspressoIdlingResource.decrement()
                }
            }
            is Resource.Error -> binding.loadingAndErrorLayout.errorTextView.text = resource.message
            else -> return
        }
    }

    override fun onClickedPopularMovies() {
        getPopularMovies()
    }

    override fun onClickedTopRatedMovies() {
        getTopRatedMovies()
    }

    override fun onClickedUpComingMovies() {
        getUpComingMovies()
    }

    override fun onClickedNowPlayingMovies() {
        getNowPlayingMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favorite_item -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}