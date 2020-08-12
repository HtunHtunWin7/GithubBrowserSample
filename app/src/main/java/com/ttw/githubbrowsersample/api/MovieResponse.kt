package com.ttw.githubbrowsersample.api

import com.google.gson.annotations.SerializedName
import com.ttw.githubbrowsersample.vo.Movie

/**
 * This is created on 8/10/20 1:30 PM.
 */
data class MovieResponse(
    @SerializedName("results")
    val movieResults: List<Movie>,
    @SerializedName("page")
    val page: Int = 0
)