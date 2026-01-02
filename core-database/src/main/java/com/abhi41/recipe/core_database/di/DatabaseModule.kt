package com.abhi41.recipe.core_database.di

import android.content.Context
import androidx.room.Room
import com.abhi41.recipe.core_database.converters.RecipesTypeConverter
import com.abhi41.recipe.core_database.dao.RecipesDao
import com.abhi41.recipe.core_database.database.RecipesDatabase
import com.abhi41.recipe.core_database.utils.Constants
import com.abhi41.recipe.core_database.utils.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context, // dagger provide us context to use this for creation of builder
    ): RecipesDatabase {
     //   val supportFactory = SupportFactory(SQLiteDatabase.getBytes("password".toCharArray()))

        return Room.databaseBuilder(
            context,
            RecipesDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            //   .openHelperFactory(supportFactory) In this way we can use sql cipher
            .addTypeConverter(RecipesTypeConverter(GsonParser(Gson())))
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase): RecipesDao {
      return database.recipeDao()
    }


}