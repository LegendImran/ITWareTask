package com.developer.code.itwaremachinetest.utils

import android.app.Application
import android.util.Log
import com.developer.code.itwaremachinetest.response.LoginModel
import com.developer.code.itwaremachinetest.response.MovieModel
import com.developer.code.itwaremachinetest.services.AuthenticationService
import com.google.gson.JsonObject
import io.reactivex.Flowable
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    var application: Application,
) {

    @Inject
    lateinit var authService: AuthenticationService


    fun authenticateUser(credential: JsonObject) : Flowable<Response<LoginModel?>> {
        return authService.authenticateUser(credential)
    }

    fun fetchMovie(request: JsonObject) : Flowable<Response<MovieModel?>> {
        return authService.fetchMovie(request)
    }

    fun changeFav(request: JsonObject) : Flowable<Response<MovieModel?>> {
        return authService.changeFav(request)
    }




}