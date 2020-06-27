package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName

data class MoviesData(
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("id") val id : Int
)