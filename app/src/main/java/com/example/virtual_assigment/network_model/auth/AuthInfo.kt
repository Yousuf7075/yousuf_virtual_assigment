package com.example.virtual_assigment.network_model.auth


import com.google.gson.annotations.SerializedName

data class AuthInfo(
    @SerializedName("assigned_to")
    val assignedTo: Int,
    @SerializedName("designation")
    val designation: Any,
    @SerializedName("is_first_login")
    val isFirstLogin: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("role_id")
    val roleId: Int,
    @SerializedName("role_name")
    val roleName: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("user_photo")
    val userPhoto: Any,
    @SerializedName("user_type")
    val userType: String,
    @SerializedName("username")
    val username: String
)