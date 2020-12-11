package com.example.virtual_assigment.network

import com.example.virtual_assigment.network_model.auth.AuthRequest
import com.example.virtual_assigment.network_model.auth.AuthResponse
import com.example.virtual_assigment.network_model.entities.EntitiesRequest
import com.example.virtual_assigment.network_model.entities.EntitiesResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("api/login/")
    fun userAuth(@Body authRequest: AuthRequest): Single<AuthResponse>

    @POST("api/v0/recruiting-entities/")
    fun postEntities(@Body entitiesRequest: EntitiesRequest): Single<EntitiesResponse>

}