package com.ttw.githubbrowsersample.di

import android.app.Application
import androidx.databinding.library.baseAdapters.BuildConfig
import androidx.room.Room
import com.ttw.githubbrowsersample.api.MovieService
import com.ttw.githubbrowsersample.api.NetworkServiceFactory
import com.ttw.githubbrowsersample.db.MovieDB
import com.ttw.githubbrowsersample.db.MovieDao
import com.ttw.githubbrowsersample.db.PreferencesHelper
import com.ttw.githubbrowsersample.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * This is created on 8/10/20 2:02 PM.
 */

@Module(includes = [ViewModelModule::class])
class AppModule {

  /*  @Singleton
    @Provides
    fun provideGithubService(): MovieService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(MovieService::class.java)
    }
*/
  @Singleton
  @Provides
  internal fun provideService(): MovieService {
      return NetworkServiceFactory.makeNetworkService(BuildConfig.DEBUG)
  }

    @Singleton
    @Provides
    fun provideDb(app: Application): MovieDB {
        return Room
            .databaseBuilder(app, MovieDB::class.java, "movie.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: MovieDB): MovieDao {
        return db.movieDao()
    }

    @Singleton
    @Provides
    internal fun providePreferencesHelper(app: Application): PreferencesHelper {
        return PreferencesHelper(app)
    }
}