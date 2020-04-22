package com.core.timeline

import com.core.timeline.exception.ErrorHandler

/**
 * @author mawenqing.
 */
interface TimelineInteractor {
    suspend fun loadCategories(onError: ErrorHandler? = null): List<TimelineCategory>

    suspend fun loadTimeline(category: String, onError: ErrorHandler? = null): List<TimelineItem>
}
