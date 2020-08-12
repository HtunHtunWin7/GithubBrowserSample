package com.ttw.githubbrowsersample.ui.nowplaying

import androidx.lifecycle.*
import com.ttw.githubbrowsersample.repository.MovieRepository
import com.ttw.githubbrowsersample.utils.AbsentLiveData
import com.ttw.githubbrowsersample.vo.Movie
import com.ttw.githubbrowsersample.vo.Resource
import com.ttw.githubbrowsersample.vo.Status
import javax.inject.Inject

/**
 * This is created on 8/10/20 4:20 PM.
 */

class NowPlayingViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    private val nextPageHandler = NextPageHandler(repository)
    val results: LiveData<Resource<List<Movie>>> = Transformations
        .switchMap(_query) {
            if (it.isNullOrBlank()) {
                AbsentLiveData.create()
            } else {
                repository.loadMovies()
            }
        }


    var loadMoreStatus: LiveData<LoadMoreState> = nextPageHandler.loadMoreState

    fun setQuery(id: String) {
        if (id == _query.value) {
            return
        }
        nextPageHandler.reset()
        _query.value = id
    }

    fun loadNextPage() {
        _query.value?.let {
            if (it.isNotBlank()) {
                nextPageHandler.queryNextPage(it)
            }
        }
    }

    fun refresh() {
        _query.value?.let {
            _query.value = it
        }
    }

    class NextPageHandler(private val repository: MovieRepository) : Observer<Resource<Boolean>> {
        private var nextPageLiveData: LiveData<Resource<Boolean>>? = null
        var loadMoreState = MutableLiveData<LoadMoreState>()
        private var query: String? = null
        private var _hasMore: Boolean = false
        val hasMore
            get() = _hasMore

        init {
            reset()
        }

        fun queryNextPage(query: String) {
            if (this.query == query) {
                return
            }
            unregister()
            this.query = query
            nextPageLiveData = repository.searchNextPage()
            loadMoreState.value =
                LoadMoreState(
                    isRunning = true,
                    errorMessage = null
                )
            nextPageLiveData?.observeForever(this)
        }

        override fun onChanged(result: Resource<Boolean>?) {
            if (result == null) {
                reset()
            } else {
                when (result.status) {
                    Status.SUCCESS -> {
                        _hasMore = result.data == true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = null
                            )
                        )
                    }
                    Status.ERROR -> {
                        _hasMore = true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = result.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        // ignore
                    }
                }
            }
        }

        private fun unregister() {
            nextPageLiveData?.removeObserver(this)
            nextPageLiveData = null
            if (_hasMore) {
                query = null
            }
        }

        fun reset() {
            unregister()
            _hasMore = true
            loadMoreState.value =
                LoadMoreState(
                    isRunning = false,
                    errorMessage = null
                )
        }
    }

    class LoadMoreState(val isRunning: Boolean, val errorMessage: String?) {
        private var handledError = false

        val errorMessageIfNotHandled: String?
            get() {
                if (handledError) {
                    return null
                }
                handledError = true
                return errorMessage
            }
    }

}