package com.core.timeline.exception

/**
 * @author mawenqing.
 */
class NetworkException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(throwable: Throwable?) : super(throwable)
}
