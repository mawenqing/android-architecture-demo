package com.app

import android.app.Application
import com.app.inject.ApplicationComponent
import com.app.inject.DaggerApplicationComponent
import com.timeline.inject.TimelineComponent

/**
 * @author mawenqing.
 */
class MyApplication : Application(), TimelineComponent.HasDependecy {
    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    private fun initializeInjector() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }

    override fun getDependency(): TimelineComponent.Dependency {
        return applicationComponent
    }
}
