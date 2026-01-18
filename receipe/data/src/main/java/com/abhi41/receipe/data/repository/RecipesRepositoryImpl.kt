package com.abhi41.receipe.data.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.abhi41.core_network.service.FoodRecipesApi
import com.abhi41.receipe.data.mappers.toDomainRecipes
import com.abhi41.receipe.data.mappers.toFoodJoke
import com.abhi41.receipe.data.mappers.toFoodJokeEntity
import com.abhi41.receipe.data.mappers.toInsertRecipes
import com.abhi41.receipe.data.mappers.toReadLocalRecipes
import com.abhi41.receipe.domain.models.FoodJoke
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.domain.repository.RecipesRepository
import com.abhi41.receipe.domain.utils.Constants
import com.abhi41.receipe.domain.utils.Resource
import com.abhi41.recipe.core_database.dao.FoodJokeDao
import com.abhi41.recipe.core_database.dao.RecipesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val TAG = "RecipesRepositoryImpl"

class RecipesRepositoryImpl(
    private val api: FoodRecipesApi,
    private val recipesDao: RecipesDao,
    private val foodJokeDao: FoodJokeDao
) : RecipesRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getRecipes(queries: Map<String, String>): Flow<Resource<List<RecipeResult>>> =
        flow {
            emit(Resource.Loading())
            try {
                val recipes: List<RecipeResult> = recipesDao.readRecipes().toReadLocalRecipes()
                emit(Resource.Loading(data = recipes))
                val result = api.getRecipies(queries)
                val response = result.results.toDomainRecipes()
                recipesDao.deleteAllRecipes()
                recipesDao.insertRecipes(response.toInsertRecipes())
                //Log.d(TAG, "getRecipes: ${response.joinToString()}")
                //     emit(Resource.Success(data = response))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops, something went wrong!", data = null))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = "Couldn't reach server, check your internet connection.",
                        data = null
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Oops, something went wrong!", data = null))
            }
            val newRecipes: List<RecipeResult> = recipesDao.readRecipes().toReadLocalRecipes()
            emit(Resource.Success(data = newRecipes))
        }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getSearchRecipes(queries: Map<String, String>): Flow<Resource<List<RecipeResult>>> = flow {
            emit(Resource.Loading())
            try {
                val result = api.searchRecipes(queries)
                val recipes = result.results.toDomainRecipes()
                emit(Resource.Loading(data = recipes))
                emit(Resource.Success(data = recipes))
            } catch (e: HttpException) {
                emit(Resource.Error(message ="Oops, something went wrong!", data = emptyList()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach server, check your internet connection.",
                    data = emptyList()))
            }
        }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getFoodJokes(): Flow<Resource<List<FoodJoke>>> = flow {
        emit(Resource.Loading())
        val foodJokes: List<FoodJoke> = foodJokeDao.readFoodJoke().map { it.toFoodJoke() }
        emit(Resource.Loading(data = foodJokes))
        try {
            val remoteFoodJokes = api.getFoodJoke(apiKey = Constants.API_KEY)
            if (remoteFoodJokes != null){
                foodJokeDao.deleteAllFoodJoke()
                foodJokeDao.insertFoodJoke(remoteFoodJokes.toFoodJokeEntity())
            }

        } catch (e: HttpException) {
            emit(Resource.Error(message ="Oops, something went wrong!", data = emptyList()))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server, check your internet connection.",
                data = emptyList()))
        }
        val newFoodJokes: List<FoodJoke> = foodJokeDao.readFoodJoke().map { it.toFoodJoke() }
        emit(Resource.Success(newFoodJokes))
    }
}