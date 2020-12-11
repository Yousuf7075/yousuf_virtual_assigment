package com.example.virtual_assigment.base

import com.example.virtual_assigment.di.MainApiUrl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseUrlModule {
    @Singleton
    @Provides
    @MainApiUrl
    fun provideBaseUrl():String = " https://recruitment.fisdev.com/"
}