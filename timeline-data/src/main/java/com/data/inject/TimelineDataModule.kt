package com.data.inject

import com.data.timeline.TimelineService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author mawenqing.
 */
@Module
class TimelineDataModule {
    @Provides
    @Singleton
    fun provideTimelineService(retrofit: Retrofit): TimelineService {
        return retrofit.create(TimelineService::class.java)
    }
}
