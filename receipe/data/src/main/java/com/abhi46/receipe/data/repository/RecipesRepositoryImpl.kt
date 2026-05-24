package com.abhi46.receipe.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import com.abhi46.core_network.service.FoodRecipesApi
import com.abhi46.receipe.data.R
import com.abhi46.receipe.data.mappers.toDomainRecipes
import com.abhi46.receipe.data.mappers.toFoodJoke
import com.abhi46.receipe.data.mappers.toFoodJokeEntity
import com.abhi46.receipe.data.mappers.toInsertRecipes
import com.abhi46.receipe.data.mappers.toReadLocalRecipes
import com.abhi46.receipe.domain.models.FoodJoke
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.domain.repository.RecipesRepository
import com.abhi46.receipe.domain.utils.Constants
import com.abhi46.receipe.domain.utils.Resource
import com.abhi46.recipe.core_database.dao.FoodJokeDao
import com.abhi46.recipe.core_database.dao.RecipesDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

private const val TAG = "RecipesRepositoryImpl"

class RecipesRepositoryImpl @Inject constructor(
    private val api: FoodRecipesApi,
    private val recipesDao: RecipesDao,
    private val foodJokeDao: FoodJokeDao,
    @ApplicationContext private val context: Context
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
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = context.getString(R.string.oops_something_went_wrong), data = null))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = context.getString(R.string.couldn_t_reach_server_check_your_internet_connection),
                        data = null
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = context.getString(R.string.oops_something_went_wrong), data = null))
            }
            val newRecipes: List<RecipeResult> = recipesDao.readRecipes().toReadLocalRecipes()
            emit(Resource.Success(data = newRecipes))
        }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getSearchRecipes(queries: Map<String, String>): Flow<Resource<List<RecipeResult>>> = flow {
            emit(Resource.Loading())
            try {
                val response = api.searchRecipes(queries)
                if (!response.isSuccessful){
                    emit(Resource.Error(
                        data = emptyList(),
                        message = context.getString(R.string.the_requested_resource_was_not_found)
                    ))
                }
                when (response.code()) {
                    HttpURLConnection.HTTP_OK -> { // Code 200
                        val recipes = response.body()?.results?.toDomainRecipes()
                        if (recipes != null) {
                            emit(Resource.Success(data = recipes))
                        } else {
                            emit(Resource.Error(message = context.getString(R.string.no_recipes_found), data = emptyList()))
                        }
                    }
                    HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_FORBIDDEN -> { // Codes 401, 403
                        emit(Resource.Error(message = context.getString(R.string.unauthorized_access_please_check_your_api_key), data = emptyList()))
                    }
                    HttpURLConnection.HTTP_NOT_FOUND -> { // Code 404
                        emit(Resource.Error(message = context.getString(R.string.the_requested_resource_was_not_found), data = emptyList()))
                    }
                    else -> { // Handle other server-side errors (5xx) or unexpected codes
                        emit(Resource.Error(message = "Server error: ${response.code()}", data = emptyList()))
                    }
                }

            } catch (e: HttpException) {
                emit(Resource.Error(message =context.getString(R.string.oops_something_went_wrong), data = emptyList()))
            } catch (e: IOException) {
                emit(Resource.Error(message = context.getString(R.string.couldn_t_reach_server_check_your_internet_connection),
                    data = emptyList()))
            }catch (e: Exception) {
                emit(Resource.Error(message = context.getString(R.string.oops_something_went_wrong), data = emptyList()))
            }

        }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getFoodJokes(): Flow<Resource<List<FoodJoke>>> = flow {
        emit(Resource.Loading())
        val foodJokes: List<FoodJoke> = foodJokeDao.readFoodJoke().map { it.toFoodJoke() }
        emit(Resource.Loading(data = foodJokes))
        try {
            val remoteFoodJokes = api.getFoodJoke(apiKey = Constants.API_KEY)
            foodJokeDao.deleteAllFoodJoke()
            foodJokeDao.insertFoodJoke(remoteFoodJokes.toFoodJokeEntity())

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