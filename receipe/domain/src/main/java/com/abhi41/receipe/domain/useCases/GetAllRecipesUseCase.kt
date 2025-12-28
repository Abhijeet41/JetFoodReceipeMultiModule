package com.abhi41.receipe.domain.useCases

import com.abhi41.receipe.domain.models.Result
import com.abhi41.receipe.domain.repository.RecipesRepository
import com.abhi41.receipe.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllRecipesUseCase @Inject constructor(
    private val repository: RecipesRepository
){

    operator fun invoke(queries: Map<String, String>): Flow<Resource<List<Result>>>{
        if (queries.isEmpty()){
            return flow {}
        }
        return repository.getRecipes(queries)
    }

}