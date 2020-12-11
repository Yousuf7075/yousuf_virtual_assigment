package com.example.virtual_assigment.ui.entities

import androidx.lifecycle.LiveData
import com.example.virtual_assigment.di.MainApiUrl
import com.example.virtual_assigment.network.ApiService
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network.wrapper.NetworkBoundResource
import com.example.virtual_assigment.network_model.entities.EntitiesRequest
import com.example.virtual_assigment.network_model.entities.EntitiesResponse
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class EntitiesRepo @Inject constructor(@MainApiUrl var api: ApiService) {
    private val disposable = CompositeDisposable()

    fun postEntities(entitiesRequest: EntitiesRequest): LiveData<ApiResponse<EntitiesResponse>> {
        return object : NetworkBoundResource<EntitiesResponse>(){
            override fun createCall(): Single<EntitiesResponse> = api.postEntities(entitiesRequest)

            override fun createDisposable(): CompositeDisposable = disposable
        }.asLiveData()
    }

    fun onClear() {
        disposable.clear()
    }
}