package com.abhi46.receipe.data.repository

import android.os.Bundle
import com.abhi46.receipe.domain.repository.AnalyticsTracker
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsTrackerImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {
    override fun logEvent(
        eventName: String,
        params: Map<String, Any>
    ) {
        val bundle = Bundle()

        params.forEach { (key, value) ->
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Boolean -> bundle.putBoolean(key, value)
            }
        }

        firebaseAnalytics.logEvent(eventName, bundle)
    }

    override fun logScreen(screenName: String) {
        firebaseAnalytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            }
        )
    }
}