package com.example.virtual_assigment.di_editable

import com.example.virtual_assigment.di.ActivityScope
import com.example.virtual_assigment.ui.auth.AuthActivity
import com.example.virtual_assigment.ui.entities.EntitiesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): AuthActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeEntitiesActivity(): EntitiesActivity

}