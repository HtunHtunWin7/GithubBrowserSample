package com.ttw.githubbrowsersample.api

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This is created on 8/10/20 1:31 PM.
 */

interface MovieService {
    @GET("now_playing")
    fun getNowPlaying(): LiveData<ApiResponse<MovieResponse>>

    @GET("now_playing")
    fun getNowPlaying(@Query("page") page: Int): Call<MovieResponse>
}