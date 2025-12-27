package com.abhi41.core_network.di

import android.content.Context
import com.abhi41.core_network.Constants
import com.abhi41.core_network.Constants.sh2561
import com.abhi41.core_network.Constants.sh2562
import com.abhi41.core_network.service.FoodRecipesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    var hostname = "api.spoonacular.com"
    //ssl pinning to prevent MIM attack
    @Singleton
    @Provides
    fun provideCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add(hostname, sh2561)
            .add(hostname, sh2562)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkhttp(
        certificatePinner: CertificatePinner,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // .certificatePinner(certificatePinner) //ssl pinning
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): FoodRecipesApi {
        return retrofit.create(FoodRecipesApi::class.java)
    }
}