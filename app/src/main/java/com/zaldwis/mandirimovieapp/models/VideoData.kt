package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("id") val id : String,
    @SerializedName("key") val key : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String
)