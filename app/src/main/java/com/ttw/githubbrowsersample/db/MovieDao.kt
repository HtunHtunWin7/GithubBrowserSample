package com.ttw.githubbrowsersample.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ttw.githubbrowsersample.vo.Movie

/**
 * This is created on 8/10/20 1:40 PM.
 */

@Dao
abstract class MovieDao{

    @Query("SELECT * FROM movie")
    abstract fun getMovies():LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movie:List<Movie>)
}