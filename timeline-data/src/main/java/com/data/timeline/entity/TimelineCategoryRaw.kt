package com.data.timeline.entity

import com.squareup.moshi.Json

/**
 * @author mawenqing.
 */
class TimelineCategoryRaw(
    @Json(name = "name") val name: String?,
    @Json(name = "data") val data: String?
)
