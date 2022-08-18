package com.developer.code.itwaremachinetest.utils

import android.content.Context
import android.util.Log
import com.developer.code.itwaremachinetest.BuildConfig
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private val TAG = ApiClient::class.java.simpleName

    var retrofit: Retrofit? = null

    var appContext :Context? = MyApplication.getContext()



    fun getRetrofit(): Api {

        if (retrofit == null) {
            val cacheSize = (5*1024*1025).toLong()
            val myCache = Cache(appContext?.cacheDir!!,cacheSize)
            val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    //Logging.i("OkHttp", "log: $message")
                }

            })
            interceptor.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }



            val client = OkHttpClient.Builder()
                .cache(myCache)
                //.addInterceptor(ResponseCodeCheckInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()


            //KeyStore.getInstance()
            val gson = GsonBuilder()
                .setLenient()
                .create()


            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()


        }
       return retrofit!!.create(Api::class.java)
    }




}