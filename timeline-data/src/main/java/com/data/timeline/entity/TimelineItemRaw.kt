package com.data.timeline.entity

import com.squareup.moshi.Json

/**
 * @author mawenqing.
 */
class TimelineItemRaw(
    @Json(name = "id") val id: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "status") val status: String?,
    @Json(name = "num_likes") val likeCount: Int,
    @Json(name = "num_comments") val commentCount: Int,
    @Json(name = "price") val price: Int,
    @Json(name = "photo") val photo: String?
)
