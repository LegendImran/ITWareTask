package com.developer.code.itwaremachinetest.utils

import com.google.gson.annotations.SerializedName

sealed class ResultHandler<out T> {
    data class Loading(
        @SerializedName("loading")
        val loading : Boolean
    ):ResultHandler<Nothing>()
    data class Success<out R>(
        @SerializedName("value")
        val value: R
    ): ResultHandler<R>()
    data class Failure<out R>(
        /*@SerializedName("code")
        val isSuccess: String?,
        @SerializedName("message")
        val messsage: String?,
        @SerializedName("throwable")
        val throwable: Throwable?*/

    val error: R?
    ): ResultHandler<R>()
}


data class ErrorBody(
    val isSuccess: Boolean,
    val messsage: String
)

