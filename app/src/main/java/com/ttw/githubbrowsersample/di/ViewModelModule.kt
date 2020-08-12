package com.ttw.githubbrowsersample.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ttw.githubbrowsersample.ui.nowplaying.NowPlayingViewModel
import com.ttw.githubbrowsersample.viewmodel.GithubViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This is created on 8/10/20 2:04 PM.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NowPlayingViewModel::class)
    abstract fun nowPlayingViewModel(nowPlayingViewModel: NowPlayingViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}