package com.app.inject

import com.core.timeline.TimelineInteractor
import com.core.timeline.TimelineInteractorImpl
import com.core.timeline.TimelineRepository
import com.data.timeline.TimelineRepositoryImpl
import com.data.timeline.TimelineService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * @author mawenqing.
 */
@Module
class ApplicationModule {
    @Provides
    @Singleton
    fun provideInteractor(repository: TimelineRepository): TimelineInteractor {
        return TimelineInteractorImpl(repository)
    }

    @Provides
    @Singleton
    fun provideRepository(service: TimelineService): TimelineRepository {
        return TimelineRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}
