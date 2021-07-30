package com.example.sample.data

import com.google.gson.annotations.SerializedName


data class Videos(

    @SerializedName("id") val id: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("duration") val duration: Int,
    @SerializedName("full_res") val full_res: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("image") val image: String,
    @SerializedName("avg_color") val avg_color: String,
    @SerializedName("user") val user: User,
    @SerializedName("video_files") val video_files: List<Video_files>,
    @SerializedName("video_pictures") val video_pictures: List<Video_pictures>
)