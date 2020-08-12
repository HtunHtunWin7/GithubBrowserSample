package com.ttw.githubbrowsersample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ttw.githubbrowsersample.AppExecutors
import com.ttw.githubbrowsersample.api.ApiSuccessResponse
import com.ttw.githubbrowsersample.api.MovieResponse
import com.ttw.githubbrowsersample.api.MovieService
import com.ttw.githubbrowsersample.db.MovieDB
import com.ttw.githubbrowsersample.db.MovieDao
import com.ttw.githubbrowsersample.db.PreferencesHelper
import com.ttw.githubbrowsersample.utils.RateLimiter
import com.ttw.githubbrowsersample.vo.Movie
import com.ttw.githubbrowsersample.vo.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * This is created on 8/10/20 3:43 PM.
 */

class MovieRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: MovieDB,
    private val movieDao: MovieDao,
    private val movieService: MovieService,
    private val preferencesHelper: PreferencesHelper
){
    private val movieRateLimit = RateLimiter<String>(10,TimeUnit.MINUTES)

    fun searchNextPage(): LiveData<Resource<Boolean>> {
        setPage()
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            page = getPage(),
            service = movieService,
            db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    private fun getPage(): Int {
        return preferencesHelper.nextPage
    }

    private fun setPage() {
        preferencesHelper.nextPage = getPage() + 1
    }

    fun loadMovies(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, MovieResponse>(appExecutors) {
            override fun saveCallResult(item: MovieResponse) {
                db.runInTransaction {
                    movieDao.insertMovies(item.movieResults.map { it })
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = movieDao.getMovies()


            override fun createCall() = movieService.getNowPlaying()

            override fun processResponse(response: ApiSuccessResponse<MovieResponse>): MovieResponse {
                return response.body
            }
        }.asLiveData()
    }
}