package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName


class ResponseReview {
    @SerializedName("results")
    val resultReview = ArrayList<ReviewData>()

    @SerializedName("total_pages")
    val total_pages : Int = 0
}