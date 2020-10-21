package com.ankitdubey021.stackexchangefeatureapp.networking
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun doGetApiCall(
            @Url url : String
    ): Response<ResponseBody>

    companion object {
        const val BASE_URL = "https://api.stackexchange.com/2.2/"
    }
}