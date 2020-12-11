package com.example.virtual_assigment.network_model.auth


import com.google.gson.annotations.SerializedName

data class AuthResponse(
        @SerializedName("app_top_module_assignment")
    val appTopModuleAssignment: List<Any>,
        @SerializedName("auth_info")
    val authInfo: AuthInfo,
        @SerializedName("organization_info")
    val organizationInfo: OrganizationInfo,
        @SerializedName("organization_logo")
    val organizationLogo: String,
        @SerializedName("organization_name")
    val organizationName: String,
        @SerializedName("success")
    val success: Boolean,
        @SerializedName("token")
    val token: String,
        @SerializedName("message")
    val message: String
)