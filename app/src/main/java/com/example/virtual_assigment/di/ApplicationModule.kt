package com.example.virtual_assigment.di

import android.app.Application
import dagger.Module
import dagger.Provides
import com.example.virtual_assigment.helper.SharedPrefHelper
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideSharedPrefHelper(application:Application) = SharedPrefHelper(application)

}