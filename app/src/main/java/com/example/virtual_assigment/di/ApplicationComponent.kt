package com.example.virtual_assigment.di

import android.app.Application
import com.example.virtual_assigment.di_editable.ActivityModule
import com.example.virtual_assigment.di_editable.FragmentModule
import com.example.virtual_assigment.di_editable.ViewModelModule
import com.example.virtual_assigment.network.module.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import com.example.virtual_assigment.base.BaseApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        FragmentModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        ApiModule::class
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(application: BaseApplication)
}