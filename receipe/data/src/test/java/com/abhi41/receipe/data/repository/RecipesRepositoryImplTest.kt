package com.abhi41.receipe.data.repository

import android.content.Context
import com.abhi41.core_network.dtos.food_joke.FoodJokeDto
import com.abhi41.core_network.dtos.food_joke.FoodRecipeDto
import com.abhi41.core_network.dtos.receipe.ResultDto
import com.abhi41.core_network.service.FoodRecipesApi
import com.abhi41.receipe.data.R
import com.abhi41.receipe.data.mappers.toDomainRecipes
import com.abhi41.receipe.data.mappers.toFoodJoke
import com.abhi41.receipe.domain.utils.Resource
import com.abhi41.recipe.core_database.dao.FoodJokeDao
import com.abhi41.recipe.core_database.dao.RecipesDao
import com.abhi41.recipe.core_database.entity.FoodJokeEntity
import com.abhi41.recipe.core_database.entity.ResultEntity
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import okio.IOException
import retrofit2.Response

class RecipesRepositoryImplTest {
    // Mocks
    private lateinit var api: FoodRecipesApi
    private lateinit var recipesDao: RecipesDao
    private lateinit var foodJokeDao: FoodJokeDao
    private lateinit var repository: RecipesRepositoryImpl
    private val context = mock(Context::class.java)

    @Before
    fun setup() {
        api = mock(FoodRecipesApi::class.java)
        recipesDao = mock(RecipesDao::class.java)
        foodJokeDao = mock(FoodJokeDao::class.java)
        repository = RecipesRepositoryImpl(api, recipesDao, foodJokeDao, context)

        // Mock string resources
        `when`(context.getString(R.string.oops_something_went_wrong)).thenReturn("Oops, something went wrong!")
        `when`(context.getString(R.string.couldn_t_reach_server_check_your_internet_connection)).thenReturn("Couldn't reach server, check your internet connection.")
        `when`(context.getString(R.string.the_requested_resource_was_not_found)).thenReturn("The requested resource was not found.")
        `when`(context.getString(R.string.no_recipes_found)).thenReturn("No recipes found.")
        `when`(context.getString(R.string.unauthorized_access_please_check_your_api_key)).thenReturn("Unauthorized access. Please check your API key.")
    }

    @Test
    fun `getRecipes returns Loading then Success with data from DB`() = runTest {
        val queryMap = getRecipesQuery()
        val apiResponse = getRecipesResponseDto()
        val resultEntity = getDummyResultEntity()

        `when`(recipesDao.readRecipes())
            .thenReturn(listOf()) 
            .thenReturn(listOf(resultEntity)) 

        `when`(api.getRecipies(queryMap)).thenReturn(apiResponse)

        val results = repository.getRecipes(queryMap).toList()

        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Loading)
        assertTrue(results[2] is Resource.Success)
        
        verify(recipesDao, times(2)).readRecipes()
        verify(api).getRecipies(queryMap)
        verify(recipesDao).deleteAllRecipes()
        verify(recipesDao).insertRecipes(org.mockito.kotlin.any())
    }


    @Test
    fun `getSearchRecipes returns Success with data`() = runTest {
        val queryMap = searchQuery()
        val apiResponse = getSearchResponseDto()
        `when`(api.searchRecipes(queryMap)).thenReturn(
            Response.success(200, apiResponse)
        )
        val results = repository.getSearchRecipes(queryMap).toList()
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Success)
        assertEquals(apiResponse.results.toDomainRecipes(), results[1].data)
    }

    @Test
    fun `test_nullOrError_SearchResponse`() = runTest {
        val queryMap = searchQuery()
        val errorMessage = "Oops, something went wrong!"
        val exception = mock(HttpException::class.java)
        
        `when`(api.searchRecipes(queryMap)).thenThrow(exception)
        
        val results = repository.getSearchRecipes(queryMap).toList()
        
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Error)
        assertEquals(errorMessage, results[1].message)
    }

    @Test
    fun `getSearchRecipes returns Error when IOException occurs`() = runTest {
        val queryMap = searchQuery()

        `when`(api.searchRecipes(queryMap)).thenAnswer {
            throw IOException("Network error")
        }

        val results = repository.getSearchRecipes(queryMap).toList()

        assertTrue(results.last() is Resource.Error)
        assertEquals(
            "Couldn't reach server, check your internet connection.",
            results.last().message
        )
    }

    @Test
    fun `getSearchRecipes return isSuccessful as false`() = runTest {
        val queryMap = searchQuery()
        `when`(api.searchRecipes(queryMap))
            .thenReturn(
                Response.error(
                    404,
                    ResponseBody.create(null,"")
                )
            )
        val results = repository.getSearchRecipes(queryMap).toList()
        assertTrue(results.last() is Resource.Error)
        assertEquals("The requested resource was not found.", results.last().message)
    }

    @Test
    fun `testBackend will throw exception`() = runTest{
        val queryMap = searchQuery()
        `when`(api.searchRecipes(queryMap))
            .thenThrow(RuntimeException("Backend error"))
        val results = repository.getSearchRecipes(queryMap).toList()
        assertTrue(results.last() is Resource.Error)
        assertEquals("Oops, something went wrong!", results.last().message)
    }

    @Test
    fun `getFood Jokes returns Success with data from DB` () = runTest {
        val apiResponse = getFoodJokeResponseDto()
        val foodJokeEntity = getFoodEntity()

        `when`(foodJokeDao.readFoodJoke())
            .thenReturn(listOf())
            .thenReturn(listOf(foodJokeEntity))

        `when`(api.getFoodJoke(org.mockito.kotlin.any()))
            .thenReturn(apiResponse)

        val results = repository.getFoodJokes().toList()
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Loading)
        assertTrue(results[2] is Resource.Success)
        assertEquals(listOf(apiResponse.toFoodJoke()), results[2].data)
    }

    private fun getSearchResponseDto(): FoodRecipeDto {
        return FoodRecipeDto(
            results = listOf(
                ResultDto(
                    aggregateLikes = 10,
                    cheap = true,
                    dairyFree = true,
                    extendedIngredients = emptyList(),
                    glutenFree = true,
                    recipeId = 1,
                    image = "https://img.spoonacular.com/recipes/715415-312x231.jpg",
                    readyInMinutes = 30,
                    sourceName = "source",
                    sourceUrl = "url",
                    summary = "summary",
                    title = "title",
                    vegan = true,
                    vegetarian = true,
                    veryHealthy = true
                )
            )
        )
    }

    private fun getRecipesResponseDto(): FoodRecipeDto {
        return FoodRecipeDto(
            results = listOf(
                ResultDto(
                    aggregateLikes = 10,
                    cheap = true,
                    dairyFree = true,
                    extendedIngredients = emptyList(),
                    glutenFree = true,
                    recipeId = 1,
                    image = "https://img.spoonacular.com/recipes/715415-312x231.jpg",
                    readyInMinutes = 30,
                    sourceName = "source",
                    sourceUrl = "url",
                    summary = "summary",
                    title = "title",
                    vegan = true,
                    vegetarian = true,
                    veryHealthy = true
                )
            )
        )
    }

    fun getDummyResultEntity(): ResultEntity {
        return ResultEntity(
            aggregateLikes = 10,
            cheap = true,
            dairyFree = true,
            extendedIngredients = emptyList(),
            glutenFree = true,
            recipeId = 1,
            image = "https://img.spoonacular.com/recipes/715415-312x231.jpg",
            readyInMinutes = 30,
            sourceName = "source",
            sourceUrl = "url",
            summary = "summary",
            title = "title",
            vegan = true,
            vegetarian = true,
            veryHealthy = true
        )
    }
    fun getFoodEntity(): FoodJokeEntity {
        return FoodJokeEntity(
            id = 1,
            text = "text"
        )
    }

    fun getFoodJokeResponseDto(): FoodJokeDto {
        return FoodJokeDto(
            text = "text"
        )
    }

    fun searchQuery(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = "4b1d5ec4278045d2a16c8bf467004700"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        queries["query"] = "pizza"
        return queries
    }

    fun getRecipesQuery(): HashMap<String, String> {
        val quries: HashMap<String, String> = HashMap()
        quries["number"] = "50"
        quries["apiKey"] = "4b1d5ec4278045d2a16c8bf467004700"
        quries["type"] = "main course"
        quries["diet"] = "gluten free"
        quries["addRecipeInformation"] = "true"
        quries["fillIngredients"] = "true"
        return quries
    }
}