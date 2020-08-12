package com.ttw.githubbrowsersample.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * This is created on 8/10/20 1:19 PM.
 */
@Entity(
    primaryKeys = ["id"]
)
class MovieDetail (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("release_date")
    val release_date: String,
    @field:SerializedName("movie_id")
    val movie_id: Int
)