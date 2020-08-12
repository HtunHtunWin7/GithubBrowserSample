package com.ttw.githubbrowsersample.di

import com.ttw.githubbrowsersample.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This is created on 8/10/20 2:03 PM.
 */


@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
