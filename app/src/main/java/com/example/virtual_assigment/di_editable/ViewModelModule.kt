package com.example.virtual_assigment.di_editable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.virtual_assigment.di.ViewModelKey
import com.example.virtual_assigment.ui.auth.AuthViewModel
import com.example.virtual_assigment.ui.entities.EntitiesViewModel
import com.example.virtual_assigment.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EntitiesViewModel::class)
    abstract fun bindEntitiesViewModel(viewModel: EntitiesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}