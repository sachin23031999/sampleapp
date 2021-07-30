package com.example.sample.data

import com.google.gson.annotations.SerializedName


data class Video_files(

    @SerializedName("id") val id: Int,
    @SerializedName("quality") val quality: String,
    @SerializedName("file_type") val file_type: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("link") val link: String
)