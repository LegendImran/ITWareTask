package com.developer.code.itwaremachinetest.services

import android.util.Log
import com.developer.code.itwaremachinetest.response.LoginModel
import com.developer.code.itwaremachinetest.response.MovieModel
import com.developer.code.itwaremachinetest.utils.Api
import com.developer.code.itwaremachinetest.utils.ApiClient
import com.google.gson.JsonObject
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationService  @Inject constructor(){

    var c = "AuthenticationService Intilized"
    private var api : Api = ApiClient.getRetrofit()

    fun authenticateUser(credential: JsonObject) : Flowable<Response<LoginModel?>> {
        return api.authenticateUserApi(credential).toFlowable(BackpressureStrategy.BUFFER)
    }

    fun fetchMovie(credential: JsonObject) : Flowable<Response<MovieModel?>> {
        return api.fetchMovieApi(credential).toFlowable(BackpressureStrategy.BUFFER)
    }

    fun changeFav(credential: JsonObject) : Flowable<Response<MovieModel?>> {
        return api.changeFavApi(credential).toFlowable(BackpressureStrategy.BUFFER)
    }




}