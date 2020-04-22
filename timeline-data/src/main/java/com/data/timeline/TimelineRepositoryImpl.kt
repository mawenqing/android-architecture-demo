package com.data.timeline

import com.core.timeline.TimelineCategory
import com.core.timeline.TimelineItem
import com.core.timeline.TimelineRepository
import com.core.timeline.exception.ErrorHandler
import com.data.timeline.entity.TimelineCategoryRaw
import com.core.timeline.exception.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author mawenqing.
 */
class TimelineRepositoryImpl @Inject constructor(
    private val service: TimelineService
) : TimelineRepository {

    private var categories = listOf<TimelineCategoryRaw>()

    override suspend fun loadCategories(onError: ErrorHandler?) =
        withContext(Dispatchers.IO) {
            try {
                loadCategoriesFromServer()
            } catch (e: Throwable) {
                onError?.invoke(NetworkException(e))
                emptyList<TimelineCategory>()
            }
        }

    override suspend fun loadTimeline(categoryName: String, onError: ErrorHandler?) =
        withContext(Dispatchers.IO) {
            try {
                loadTimelineFromServer(categoryName)
            } catch (e: Throwable) {
                onError?.invoke(
                    NetworkException(e)
                )
                emptyList<TimelineItem>()
            }
        }

    private suspend fun loadCategoriesFromServer(): List<TimelineCategory> {
        categories = service.getTimelineCategories()
            .filterNotNull()
            .filter { !it.name.isNullOrEmpty() && !it.data.isNullOrEmpty() }
        return categories.map { TimelineCategory(it.name!!) }
    }

    private suspend fun loadTimelineFromServer(categoryName: String): List<TimelineItem> {
        val url = categories.firstOrNull { it.name == categoryName }?.data
        if (!checkUrl(url)) {
            throw IllegalArgumentException("Invalid address")
        }
        return service.getTimelineByCategory(url!!)
            .filterNotNull()
            .map {
                TimelineItem(
                    it.id ?: "",
                    it.name ?: "",
                    it.status ?: "",
                    it.likeCount,
                    it.commentCount,
                    it.price,
                    it.photo ?: ""
                )
            }
    }

    private fun checkUrl(url: String?): Boolean {
        return url?.startsWith("http") ?: false
    }
}
