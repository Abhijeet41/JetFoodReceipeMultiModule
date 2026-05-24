package com.abhi46.receipe.data.di

import com.abhi46.receipe.data.repository.PreferencesRepositoryImpl
import com.abhi46.receipe.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(
        impl: PreferencesRepositoryImpl
    ): PreferencesRepository {
        return impl
    }

}