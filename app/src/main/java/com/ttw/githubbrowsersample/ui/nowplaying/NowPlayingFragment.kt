package com.ttw.githubbrowsersample.ui.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ttw.githubbrowsersample.AppExecutors
import com.ttw.githubbrowsersample.R
import com.ttw.githubbrowsersample.binding.FragmentDataBindingComponent
import com.ttw.githubbrowsersample.databinding.FragmentMovieBinding
import com.ttw.githubbrowsersample.di.Injectable
import com.ttw.githubbrowsersample.ui.common.RepoListAdapter
import com.ttw.githubbrowsersample.ui.common.RetryCallback
import com.ttw.githubbrowsersample.utils.autoCleared
import kotlinx.android.synthetic.main.movie_item.view.*
import javax.inject.Inject

/**
 * This is created on 8/10/20 3:13 PM.
 */
class NowPlayingFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: NowPlayingViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<FragmentMovieBinding>()

    var adapter by autoCleared<RepoListAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        val rvAdapter = RepoListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) {

        }

        binding.recycler.adapter = rvAdapter
        this.adapter = rvAdapter
        viewModel.setQuery("start")

        binding.callback = object : RetryCallback {
            override fun retry() {
                viewModel.refresh()
            }
        }
    }

    private fun initRecyclerView() {
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    viewModel.loadNextPage()
                }
            }
        })
        binding.movieList = viewModel.results
        viewModel.results.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.data)
            Toast.makeText(context,it.data?.size.toString(),Toast.LENGTH_LONG)
        })

        viewModel.loadMoreStatus.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null) {
                binding.loadingMore = false
            } else {
                binding.loadingMore = loadingMore.isRunning
                val error = loadingMore.errorMessageIfNotHandled
                if (error != null) {
                    Snackbar.make(binding.loadMoreBar, error, Snackbar.LENGTH_LONG).show()
                }
            }

        })
    }

}