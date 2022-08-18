package com.developer.code.itwaremachinetest.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.code.itwaremachinetest.response.LoginModel
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
class AuthentiCationViewModel  @Inject constructor(var repository: AppRepository) : ViewModel()  {

    private val autheResponse : MutableLiveData<ResultHandler<LoginModel?>> = MutableLiveData();

    var compositeDisposable : CompositeDisposable = CompositeDisposable()


    fun authenticateUser(user: String, passwrd: String,context: Context) {
        val request = JsonObject()
        request.addProperty("user_name",user)
        request.addProperty("password",passwrd)
        compositeDisposable.add(
            repository.authenticateUser(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isSuccessful){
                        autheResponse.value = ResultHandler.Success(it.body())
                    }else{
                        autheResponse.value = ResultHandler.Failure(MyApplication.getGson().fromJson(it.errorBody()?.string(),LoginModel::class.java))
                    }
                },{
                    Toast.makeText(context,"Something Went wrong!!",Toast.LENGTH_SHORT).show()
                })
        )
    }

    fun observeAuthResponse(): LiveData<ResultHandler<LoginModel?>> {
        return autheResponse
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}