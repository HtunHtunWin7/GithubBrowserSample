package com.ttw.githubbrowsersample

import android.app.Activity
import android.app.Application
import com.ttw.githubbrowsersample.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * This is created on 8/10/20 3:07 PM.
 */

class MovieApp :Application(), HasActivityInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
    override fun activityInjector() = dispatchingAndroidInjector

}