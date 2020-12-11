package com.example.virtual_assigment.network_model.entities


import com.google.gson.annotations.SerializedName

data class CvFileRQ(
    @SerializedName("tsync_id")
    val tsyncId: String
)