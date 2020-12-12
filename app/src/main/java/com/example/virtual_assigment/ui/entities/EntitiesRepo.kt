package com.example.virtual_assigment.ui.entities

import androidx.lifecycle.LiveData
import com.example.virtual_assigment.di.MainApiUrl
import com.example.virtual_assigment.network.ApiService
import com.example.virtual_assigment.network.wrapper.ApiResponse
import com.example.virtual_assigment.network.wrapper.NetworkBoundResource
import com.example.virtual_assigment.network_model.entities.EntitiesRequest
import com.example.virtual_assigment.network_model.entities.EntitiesResponse
import com.example.virtual_assigment.network_model.upload_file.FileUploadResponse
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class EntitiesRepo @Inject constructor(@MainApiUrl var api: ApiService) {
    private val disposable = CompositeDisposable()

    fun postEntities(entitiesRequest: EntitiesRequest): LiveData<ApiResponse<EntitiesResponse>> {
        Timber.e("sending entities information...")

        return object : NetworkBoundResource<EntitiesResponse>(){
            override fun createCall(): Single<EntitiesResponse> = api.postEntities(entitiesRequest)

            override fun createDisposable(): CompositeDisposable = disposable
        }.asLiveData()
    }

    fun uploadFile(file_token_id: Int, file: File): LiveData<ApiResponse<FileUploadResponse>>{
        val fileName = file.name
        Timber.e("sending file name : $fileName")

        val part = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody("application/pdf".toMediaTypeOrNull()))

        return object : NetworkBoundResource<FileUploadResponse>(){
            override fun createCall(): Single<FileUploadResponse> = api.uploadFile(file_token_id, part)

            override fun createDisposable(): CompositeDisposable = disposable
        }.asLiveData()
    }

    fun onClear() {
        disposable.clear()
    }
}