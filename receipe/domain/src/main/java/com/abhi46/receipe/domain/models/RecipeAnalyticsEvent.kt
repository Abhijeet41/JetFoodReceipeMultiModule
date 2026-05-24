package com.abhi46.receipe.domain.models

data class RecipeAnalyticsEvent(
    val eventId: String,
    val recipeName: String,
    val screenName: String,
    val timestamp: Long = System.currentTimeMillis()
)
