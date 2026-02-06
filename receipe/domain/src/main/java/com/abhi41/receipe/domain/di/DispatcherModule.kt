package com.abhi41.receipe.domain.di

import com.abhi41.receipe.domain.utils.AppDispatcherProvider
import com.abhi41.receipe.domain.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider {
        return AppDispatcherProvider()
    }
}