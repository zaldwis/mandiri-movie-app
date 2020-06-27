package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName


class ResponseMovie {
    @SerializedName("backdrop_path")
    val backdrop_path : String? = null

    @SerializedName("genres")
    val genreData = ArrayList<GenreData>()

    @SerializedName("id")
    val id : Int = 0

    @SerializedName("overview")
    val overview : String? = null

    @SerializedName("poster_path")
    val poster_path : String? = null

    @SerializedName("release_date")
    val release_date : String? = null

    @SerializedName("runtime")
    val runtime : Int = 0

    @SerializedName("title")
    val title : String? = null
}