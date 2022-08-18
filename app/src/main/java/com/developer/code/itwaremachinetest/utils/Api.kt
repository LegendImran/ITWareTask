package com.developer.code.itwaremachinetest.utils

import com.developer.code.itwaremachinetest.response.LoginModel
import com.developer.code.itwaremachinetest.response.MovieModel
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/api/api/login/login")
    fun authenticateUserApi(@Body request: JsonObject): Observable<Response<LoginModel?>>

    @POST("/api/api/movies/movies")
    fun fetchMovieApi(@Body request: JsonObject): Observable<Response<MovieModel?>>

    @POST("/api/api/movies/modify_favourite")
    fun changeFavApi(@Body request: JsonObject): Observable<Response<MovieModel?>>





}