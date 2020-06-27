package com.zaldwis.androidfundamental1.Services

import com.zaldwis.mandirimovieapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("genre/movie/list")
    fun getAllGenres(
        @Query("api_key") api_key: String?,
        @Query("language") language: String?
    ): Call<ResponseData>

    @GET("discover/movie")
    fun getMoviebyGenre(
        @Query("api_key") api_key: String?,
        @Query("with_genres") with_genres: Int?,
        @Query("page") page: Int?,
        @Query("sort_by") sort_by: String?,
        @Query("include_adult") include_adult: Boolean?,
        @Query("include_video") include_video: Boolean?,
        @Query("language") language: String?
    ): Call<ResponseData2>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String?,
        @Query("language") language: String?
    ): Call<ResponseMovie>

    @GET("movie/{movie_id}/reviews")
    fun getReview(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String?,
        @Query("language") language: String?,
        @Query("page") page: Int
    ): Call<ResponseReview>

    @GET("movie/{movie_id}/videos")
    fun getVideos(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String?,
        @Query("language") language: String?
    ): Call<ResponseVideo>
}