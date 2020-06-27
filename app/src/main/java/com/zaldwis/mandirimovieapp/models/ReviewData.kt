package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName

data class ReviewData(
    @SerializedName("author") val author : String,
    @SerializedName("content") val content : String
)