package com.example.virtual_assigment.network.module

import com.example.virtual_assigment.di.GoogleMapUrl
import com.example.virtual_assigment.di.MainApiUrl
import dagger.Module
import dagger.Provides
import com.example.virtual_assigment.network.ApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        RetrofitModule::class
    ]
)
open class ApiModule {

    @Singleton
    @Provides
    @MainApiUrl
    fun provideMainApi(@MainApiUrl retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    @GoogleMapUrl
    fun provideGoogleMapApi(@GoogleMapUrl retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}