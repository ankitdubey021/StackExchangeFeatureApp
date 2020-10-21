package com.ankitdubey021.stackexchangefeatureapp.di

import com.ankitdubey021.stackexchangefeatureapp.networking.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule{
    var defaultTimeout = 180

    @Provides
    @Singleton
    fun provideCall(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(defaultTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(defaultTimeout.toLong(), TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)

}