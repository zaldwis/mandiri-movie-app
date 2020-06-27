package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName

data class GenreData(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String
)