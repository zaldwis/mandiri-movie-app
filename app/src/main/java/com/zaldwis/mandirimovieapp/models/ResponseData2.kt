package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName


class ResponseData2 {
    @SerializedName("results")
    val resultMoviebyGenre = ArrayList<MoviesData>()

    @SerializedName("total_pages")
    val total_pages : Int = 0
}