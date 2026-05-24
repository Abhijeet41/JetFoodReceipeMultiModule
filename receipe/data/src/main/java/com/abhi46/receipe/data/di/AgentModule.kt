package com.abhi46.receipe.data.di

import com.abhi46.receipe.data.agent.RecipeAgentImpl
import com.abhi46.receipe.domain.agent.RecipeAgent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AgentModule {

    @Binds
    @Singleton
    abstract fun bindRecipeAgent(
        recipeAgentImpl: RecipeAgentImpl
    ): RecipeAgent
}
