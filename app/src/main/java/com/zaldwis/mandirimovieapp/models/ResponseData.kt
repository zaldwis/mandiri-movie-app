package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName


class ResponseData {
    @SerializedName("genres")
    val genreData = ArrayList<GenreData>()
}