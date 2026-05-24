package com.abhi46.receipe.domain.useCases

import com.abhi46.receipe.domain.models.FoodJoke
import com.abhi46.receipe.domain.repository.RecipesRepository
import com.abhi46.receipe.domain.utils.DispatcherProvider
import com.abhi46.receipe.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FoodJokeUsecase @Inject constructor(
    private val  repository: RecipesRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    operator fun invoke(): Flow<Resource<List<FoodJoke>>>{
        return repository.getFoodJokes().flowOn(dispatcherProvider.io)
    }
}