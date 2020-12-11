package com.example.virtual_assigment.network_model.auth


import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)