package com.example.virtual_assigment.network_model.auth


import com.google.gson.annotations.SerializedName

data class OrganizationInfo(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("organization_status")
    val organizationStatus: Int,
    @SerializedName("project_name")
    val projectName: String,
    @SerializedName("translated_project_name")
    val translatedProjectName: String,
    @SerializedName("vat_registration_number")
    val vatRegistrationNumber: String
)