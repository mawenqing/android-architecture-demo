package com.core.timeline

import com.core.timeline.exception.ErrorHandler

/**
 * @author mawenqing.
 */
interface TimelineRepository {
    suspend fun loadCategories(onError: ErrorHandler? = null): List<TimelineCategory>

    suspend fun loadTimeline(
        categoryName: String,
        onError: ErrorHandler? = null
    ): List<TimelineItem>
}
