package com.app.inject

import com.app.MyApplication
import com.data.inject.NetworkModule
import com.data.inject.TimelineDataModule
import com.timeline.inject.TimelineComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author mawenqing.
 */
@Singleton
@Component(modules = [ApplicationModule::class, TimelineDataModule::class, NetworkModule::class])
interface ApplicationComponent : TimelineComponent.Dependency {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): ApplicationComponent
    }
}
