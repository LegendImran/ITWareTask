package com.developer.code.itwaremachinetest.utils

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application() {


    init {
        appinstance = this
        gsons  = Gson()
    }

    companion object{

        var appinstance : MyApplication? = null
        var gsons : Gson? = null
        fun getContext(): Context? {
            return  appinstance?.applicationContext
        }

        fun getGson():Gson{
            return gsons!!
        }
    }
}