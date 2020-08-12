package com.ttw.githubbrowsersample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ttw.githubbrowsersample.vo.Movie
import com.ttw.githubbrowsersample.vo.Next

/**
 * This is created on 8/10/20 1:36 PM.
 */

@Database(entities = [Movie::class, Next::class], version = 2, exportSchema = false)
abstract class MovieDB : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}