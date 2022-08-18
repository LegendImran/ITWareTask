package com.developer.code.itwaremachinetest.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.code.itwaremachinetest.response.Data
import com.developer.code.itwaremachinetest.response.LoginModel
import com.developer.code.itwaremachinetest.response.MovieModel
import com.developer.code.itwaremachinetest.utils.AppRepository
import com.developer.code.itwaremachinetest.utils.ErrorBody
import com.developer.code.itwaremachinetest.utils.MyApplication
import com.developer.code.itwaremachinetest.utils.ResultHandler
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class MovieViewModel  @Inject constructor(var repository: AppRepository) : ViewModel()  {

    private val movieRes : MutableLiveData<ResultHandler<MovieModel?>> = MutableLiveData();


    var compositeDisposable : CompositeDisposable = CompositeDisposable()

    fun fetchMovie(context: Context,user:String = "user2"){
        movieRes.value = ResultHandler.Loading(true)
        val request = JsonObject()
        request.addProperty("user_name",user)
        compositeDisposable.add(
            repository.fetchMovie(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        movieRes.value = ResultHandler.Success(it.body())
                    }else{
                        movieRes.value = ResultHandler.Failure(
                            MyApplication.getGson().fromJson(it.errorBody()?.string(),
                                MovieModel::class.java))
                    }
                },{
                    movieRes.value = ResultHandler.Failure(null)

                    Toast.makeText(context,"Something Went wrong!!", Toast.LENGTH_SHORT).show()
                })
        )
    }

    fun changeFav(context:Context,movie: Data,user : String) {
        val request = JsonObject()
        request.addProperty("user_name",user)
        request.addProperty("movie_id",movie.movie_id)
        compositeDisposable.add(
            repository.changeFav(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        fetchMovie(context,user)
                    }else{
                        movieRes.value = ResultHandler.Failure(
                            MyApplication.getGson().fromJson(it.errorBody()?.string(),
                                MovieModel::class.java))
                    }
                },{
                    movieRes.value = ResultHandler.Failure(null)

                    Toast.makeText(context,"Something Went wrong!!", Toast.LENGTH_SHORT).show()
                })
        )

    }

    fun observeMovieRes(): LiveData<ResultHandler<MovieModel?>> {
        return movieRes
    }

}