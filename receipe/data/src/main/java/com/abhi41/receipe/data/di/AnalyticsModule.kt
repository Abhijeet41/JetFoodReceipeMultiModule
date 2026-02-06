package com.abhi41.receipe.data.di

import android.content.Context
import com.abhi41.receipe.data.repository.FirebaseAnalyticsTrackerImpl
import com.abhi41.receipe.domain.repository.AnalyticsTracker
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsBindModule {

    @Binds
    @Singleton
    abstract fun bindAnalyticsTracker(
        analyticsTrackerImpl: FirebaseAnalyticsTrackerImpl
    ): AnalyticsTracker
}

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsProvideModule {

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(
        @ApplicationContext context: Context
    ): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }
}