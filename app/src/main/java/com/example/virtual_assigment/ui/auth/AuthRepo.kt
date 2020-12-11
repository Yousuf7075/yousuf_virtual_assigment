package com.example.virtual_assigment.ui.auth

import androidx.lifecycle.LiveData
import com.example.virtual_assigment.di.MainApiUrl
import com.example.virtual_assigment.network.ApiService
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network.wrapper.NetworkBoundResource
import com.example.virtual_assigment.network_model.AuthRequest
import com.example.virtual_assigment.network_model.AuthResponse
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AuthRepo @Inject constructor(@MainApiUrl var api: ApiService) {
    private val disposable = CompositeDisposable()

    fun userAuth(authRequest: AuthRequest): LiveData<ApiResponse<AuthResponse>> {
        return object : NetworkBoundResource<AuthResponse>(){
            override fun createCall(): Single<AuthResponse> = api.userAuth(authRequest)

            override fun createDisposable(): CompositeDisposable = disposable
        }.asLiveData()
    }

    fun onClear() {
        disposable.clear()
    }
}