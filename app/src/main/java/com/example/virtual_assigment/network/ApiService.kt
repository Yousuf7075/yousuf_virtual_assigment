package com.example.virtual_assigment.network

import com.example.virtual_assigment.network_model.auth.AuthRequest
import com.example.virtual_assigment.network_model.auth.AuthResponse
import com.example.virtual_assigment.network_model.entities.EntitiesRequest
import com.example.virtual_assigment.network_model.entities.EntitiesResponse
import com.example.virtual_assigment.network_model.upload_file.FileUploadResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiService {

    @POST("api/login/")
    fun userAuth(@Body authRequest: AuthRequest): Single<AuthResponse>

    @POST("api/v0/recruiting-entities/")
    fun postEntities(@Body entitiesRequest: EntitiesRequest): Single<EntitiesResponse>

    @Multipart
    @POST("api/file-object/{file_token_id}/")
    fun uploadFile(
            @Path("file_token_id") file_token_id: Int,
            @Part file: MultipartBody.Part
    ): Single<FileUploadResponse>

}