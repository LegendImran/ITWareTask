package com.developer.code.itwaremachinetest.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.code.itwaremachinetest.adapter.MovieAdapter
import com.developer.code.itwaremachinetest.databinding.ActivityMovieListBinding
import com.developer.code.itwaremachinetest.response.Data
import com.developer.code.itwaremachinetest.utils.ResultHandler
import com.developer.code.itwaremachinetest.viewModels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    lateinit var binding : ActivityMovieListBinding
    val movieVM: MovieViewModel by viewModels()
    lateinit var movieAdapter : MovieAdapter
    var movielist =  ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        addObserver()

    }

    private fun setUp(){
        movieAdapter = MovieAdapter(this,movielist){
            changeStatus(it)
        }
       LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
           .also {
           val dividerItemDecoration = DividerItemDecoration(
               binding.rvMovie.getContext(), it.orientation)
               binding.rvMovie.layoutManager = it
               binding.rvMovie.addItemDecoration(dividerItemDecoration)
               binding.rvMovie.adapter = movieAdapter
       }
        intent.getStringExtra("USERNAME")?.let {
            movieVM.fetchMovie(this,it)
        }
    }

    private fun changeStatus(movie: Data) {
        movieVM.changeFav(this,movie,intent.getStringExtra("USERNAME")?:"")
    }

    private fun addObserver(){
        movieVM.observeMovieRes().observe(this){
            when(it){
                is ResultHandler.Success ->{
                    it.value?.let { res->
                        if(res.message.equals("Success")){
                            movielist.clear()
                            movielist.addAll(res.data)
                            movieAdapter.notifyDataSetChanged()
                        }else{
                            Toast.makeText(this,res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is ResultHandler.Failure ->{
                    Toast.makeText(this,it.error?.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}