package com.example.sample.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.sample.data.Video_files

@Entity
data class Details(
    @PrimaryKey
    val id: Int,
    val duration: Int,
    val image: String,
    val user_name: String,
    val user_url: String,
    val video_url: String,
  //  val video_list: List<Video_files>
)