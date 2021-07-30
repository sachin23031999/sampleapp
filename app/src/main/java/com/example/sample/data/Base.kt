package com.example.sample.data

import com.google.gson.annotations.SerializedName


data class Base(

	@SerializedName("page") val page: Int,
	@SerializedName("per_page") val per_page: Int,
	@SerializedName("videos") val videos: List<Videos>,
	@SerializedName("total_results") val total_results: Int,
	@SerializedName("next_page") val next_page: String,
	@SerializedName("url") val url: String
)