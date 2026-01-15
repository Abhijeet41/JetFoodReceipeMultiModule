package com.abhi41.receipe.domain.useCases
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.domain.repository.RecipesRepository
import com.abhi41.receipe.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchResultUseCase @Inject constructor(
    private val repository: RecipesRepository
) {
    operator fun invoke(queries: Map<String, String>): Flow<Resource<List<RecipeResult>>> {
        if (queries.isEmpty()) {
            return flow { }
        }
        return repository.getSearchRecipes(queries = queries)
    }

}