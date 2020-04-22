package com.core.timeline

import com.core.timeline.exception.ErrorHandler
import javax.inject.Inject

/**
 * @author mawenqing.
 */
class TimelineInteractorImpl @Inject constructor(private val repository: TimelineRepository) :
    TimelineInteractor {

    override suspend fun loadCategories(onError: ErrorHandler?) = repository.loadCategories(onError)

    override suspend fun loadTimeline(
        category: String,
        onError: ErrorHandler?
    ): List<TimelineItem> =
        repository.loadTimeline(category, onError)
}
