package com.example.sample.data

import com.google.gson.annotations.SerializedName


data class Video_pictures(

    @SerializedName("id") val id: Int,
    @SerializedName("nr") val nr: Int,
    @SerializedName("picture") val picture: String
)