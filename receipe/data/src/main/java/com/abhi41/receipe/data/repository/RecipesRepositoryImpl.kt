package com.abhi41.receipe.data.repository

import android.content.Context
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.abhi41.core_network.dtos.receipe.ResultDto
import com.abhi41.core_network.service.FoodRecipesApi
import com.abhi41.receipe.data.mappers.toDomainRecipes
import com.abhi41.receipe.domain.models.Result
import com.abhi41.receipe.domain.repository.RecipesRepository
import com.abhi41.receipe.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

private const val TAG = "RecipesRepositoryImpl"
class RecipesRepositoryImpl(
    private val api: FoodRecipesApi,
) : RecipesRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getRecipes(queries: Map<String, String>): Flow<Resource<List<Result>>> = flow {
        try {
            val result = api.getRecipies(queries)
            val response = result.results.toDomainRecipes()
            Log.d(TAG, "getRecipes: ${response[0].title}")

            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(message = "Oops, something went wrong!", data = null))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(message = "Couldn't reach server, check your internet connection.", data = null))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Error(message = "Oops, something went wrong!", data = null))
        }
    }

}