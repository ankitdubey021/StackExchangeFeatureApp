package com.ankitdubey021.stackexchangefeatureapp.data

import okhttp3.ResponseBody


sealed class State<T> {

    class Loading<T> : State<T>()
    data class Success<T>(val data: T) : State<T>()
    data class Error<T>(val message: ResponseBody) : State<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: ResponseBody) =Error<T>(message)
    }
}