package com.core.timeline

/**
 * @author mawenqing.
 */
class TimelineItem(
    val id: String,
    val name: String,
    val status: String,
    val likeCount: Int,
    val commentCount: Int,
    val price: Int,
    val photo: String
)

const val STATUS_SOLD_OUT = "sold_out"
