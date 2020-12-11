package com.example.virtual_assigment.base

import android.app.Application
import com.example.virtual_assigment.helper.SharedPrefHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import com.example.virtual_assigment.helper.FontsOverride
import com.example.virtual_assigment.BuildConfig
import com.example.virtual_assigment.di.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

class BaseApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var helper: SharedPrefHelper


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/OpenSans-Regular.ttf")
        DaggerApplicationComponent.builder().application(this).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}