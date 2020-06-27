package com.zaldwis.mandirimovieapp.models

import com.google.gson.annotations.SerializedName


class ResponseVideo {
    @SerializedName("results")
    val resultVideo = ArrayList<VideoData>()

}