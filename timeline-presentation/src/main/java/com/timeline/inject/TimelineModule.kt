package com.timeline.inject

import com.core.timeline.TimelineInteractor
import com.core.timeline.TimelineScope
import com.timeline.tabhost.SectionsPagerAdapter
import com.timeline.tabhost.TimelineTabHostActivity
import com.timeline.list.TimelineListViewModel
import com.timeline.tabhost.TimelineTabViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author mawenqing.
 */
@Module
class TimelineModule {
    @TimelineScope
    @Provides
    fun provideTimelinePagerAdapter(activity: TimelineTabHostActivity): SectionsPagerAdapter {
        return SectionsPagerAdapter(
            activity.supportFragmentManager
        )
    }

    @Provides
    fun provideListViewModelFactory(
        interactor: TimelineInteractor,
        mainDispatcher: CoroutineDispatcher
    ): TimelineListViewModel.Factory {
        return TimelineListViewModel.Factory(interactor, mainDispatcher)
    }

    @Provides
    fun provideTabViewModelFactory(
        interactor: TimelineInteractor,
        mainDispatcher: CoroutineDispatcher
    ): TimelineTabViewModel.Factory {
        return TimelineTabViewModel.Factory(interactor, mainDispatcher)
    }
}
