package com.abhi41.receipe.domain.repository

interface AnalyticsTracker {

    fun logEvent(
        eventName: String,
        params: Map<String, Any>
    )

    fun logScreen(
        screenName: String
    )

}