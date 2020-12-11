package com.example.virtual_assigment.network

import com.example.virtual_assigment.network_model.AuthRequest
import com.example.virtual_assigment.network_model.AuthResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @POST("api/login/")
    fun userAuth(@Body authRequest: AuthRequest): Single<AuthResponse>

}