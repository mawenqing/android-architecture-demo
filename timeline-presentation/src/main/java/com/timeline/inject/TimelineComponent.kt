package com.timeline.inject

import com.core.timeline.TimelineInteractor
import com.core.timeline.TimelineScope
import com.timeline.tabhost.TimelineTabHostActivity
import com.timeline.list.TimelineListFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author mawenqing.
 */
@TimelineScope
@Component(modules = [TimelineModule::class], dependencies = [TimelineComponent.Dependency::class])
interface TimelineComponent {
    fun inject(activity: TimelineTabHostActivity)
    fun inject(fragment: TimelineListFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: TimelineTabHostActivity): Builder

        fun dependency(dependency: Dependency): Builder

        fun build(): TimelineComponent
    }

    interface ComponentProvider {
        val timelineComponent: TimelineComponent
    }

    interface Dependency {
        fun interactor(): TimelineInteractor
        fun mainDispatcher(): CoroutineDispatcher
    }

    interface HasDependecy {
        fun getDependency(): Dependency
    }
}
