package com.ttw.githubbrowsersample.di

import com.ttw.githubbrowsersample.ui.nowplaying.NowPlayingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This is created on 8/10/20 2:05 PM.
 */


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeNowPlayingFragment(): NowPlayingFragment
}