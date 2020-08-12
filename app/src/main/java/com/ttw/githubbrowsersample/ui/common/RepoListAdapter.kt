package com.ttw.githubbrowsersample.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.ttw.githubbrowsersample.AppExecutors
import com.ttw.githubbrowsersample.R
import com.ttw.githubbrowsersample.databinding.MovieItemBinding
import com.ttw.githubbrowsersample.vo.Movie

class RepoListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val repoClickCallback: ((Movie) -> Unit)?
) : DataBoundListAdapter<Movie, MovieItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.overview == newItem.overview
                    && oldItem.release_date == newItem.release_date
        }
    }
) {

    override fun createBinding(parent: ViewGroup): MovieItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: MovieItemBinding, item: Movie) {
        binding.movie = item
    }
}