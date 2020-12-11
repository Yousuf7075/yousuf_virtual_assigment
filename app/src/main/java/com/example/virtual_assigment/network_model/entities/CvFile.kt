package com.example.virtual_assigment.network_model.entities


import com.google.gson.annotations.SerializedName

data class CvFile(
    @SerializedName("code")
    val code: String,
    @SerializedName("date_created")
    val dateCreated: Long,
    @SerializedName("description")
    val description: Any,
    @SerializedName("extension")
    val extension: Any,
    @SerializedName("file")
    val `file`: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("last_updated")
    val lastUpdated: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("tsync_id")
    val tsyncId: String
)