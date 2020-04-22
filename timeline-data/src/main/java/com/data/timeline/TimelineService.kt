package com.data.timeline

import com.data.timeline.entity.TimelineCategoryRaw
import com.data.timeline.entity.TimelineItemRaw
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @author mawenqing.
 */
interface TimelineService {
    @Throws(Exception::class)
    @GET(TimelineApi.apiUrl)
    suspend fun getTimelineCategories(): List<TimelineCategoryRaw?>

    @Throws(Exception::class)
    @GET
    suspend fun getTimelineByCategory(@Url address: String): List<TimelineItemRaw?>
}
