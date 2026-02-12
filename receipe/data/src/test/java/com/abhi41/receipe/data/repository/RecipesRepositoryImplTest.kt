package com.abhi41.receipe.data.repository

import android.util.Log
import com.abhi41.core_network.dtos.food_joke.FoodJokeDto
import com.abhi41.core_network.dtos.food_joke.FoodRecipeDto
import com.abhi41.core_network.dtos.receipe.ResultDto
import com.abhi41.core_network.service.FoodRecipesApi
import com.abhi41.receipe.data.mappers.toDomainRecipes
import com.abhi41.receipe.data.mappers.toFoodJoke
import com.abhi41.receipe.data.mappers.toFoodJokeEntity
import com.abhi41.receipe.domain.models.FoodJoke
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.domain.utils.Resource
import com.abhi41.recipe.core_database.dao.FoodJokeDao
import com.abhi41.recipe.core_database.dao.RecipesDao
import com.abhi41.recipe.core_database.entity.FoodJokeEntity
import com.abhi41.recipe.core_database.entity.ResultEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyMap
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.HttpException
import okio.IOException
import retrofit2.Response
import retrofit2.http.Query

class RecipesRepositoryImplTest {
    // Mocks
    private lateinit var api: FoodRecipesApi
    private lateinit var recipesDao: RecipesDao
    private lateinit var foodJokeDao: FoodJokeDao
    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setup() {
        api = mock(FoodRecipesApi::class.java)
        recipesDao = mock(RecipesDao::class.java)
        foodJokeDao = mock(FoodJokeDao::class.java)
        repository = RecipesRepositoryImpl(api, recipesDao, foodJokeDao)
    }

    @Test
    fun `getRecipes returns Loading then Success with data from DB`() = runTest {
        // 1. Arrange (Prepare data and Mocks)
        val queryMap = getRecipesQuery()
        val apiResponse = getRecipesResponseDto()
        // Create a dummy entity that represents what is stored in DB
        // NOTE: You need to instantiate your actual RecipesEntity here.
        val resultEntity = getDummyResultEntity()
        // Stubbing:
        // First call to readRecipes returns empty (simulating empty cache)
        // Second call (after API success) returns data
        `when`(recipesDao.readRecipes())
            .thenReturn(listOf()) // First emission
            .thenReturn(listOf(resultEntity)) // Second emission (after insert)

        `when`(api.getRecipies(queryMap)).thenReturn(apiResponse)
        // 2. Act (Execute the function)
        // We collect the flow into a list to verify all emitted states
        val results = repository.getRecipes(queryMap).toList()
        // 3. Assert (Verify the results)
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Loading)
        assertTrue(results[2] is Resource.Success)
        // Verify interactions
        verify(recipesDao, times(2)).readRecipes()
        verify(api).getRecipies(queryMap)
        verify(recipesDao).deleteAllRecipes()
        verify(recipesDao).insertRecipes(listOf(getDummyResultEntity())) // Hard to verify exact object due to mapping, but we know it was called

    }


    @Test
    fun `getSearchRecipes returns Success with data`() = runTest {
        // 1. Arrange
        val queryMap = searchQuery()
        val apiResponse = getSearchResponseDto()
        `when`(api.searchRecipes(queryMap)).thenReturn(
            Response.success(200, apiResponse)
        )
        // 2. Act
        val results = repository.getSearchRecipes(queryMap).toList()
        // 3. Assert
        // Logic in Repo: Loading -> Loading(data) -> Success(data)
        assertTrue(results[0] is Resource.Loading)
        //assertTrue(results[1] is Resource.Loading)
        assertTrue(results[1] is Resource.Success)
        assertEquals(apiResponse.results.toDomainRecipes(), results[1].data)

    }

    @Test
    fun `test_nullOrError_SearchResponse`() = runTest {
        // 1. Arrange
        val queryMap = searchQuery()
        val errorMessage = "Oops, something went wrong!"

        // We need to simulate an HttpException.
        // Note: Creating an HttpException usually requires a Retrofit Response.
        // Or simpler: Mock the exception class itself.
        val exception = mock(HttpException::class.java)
        // TELL MOCKITO TO THROW AN ERROR
        `when`(api.searchRecipes(queryMap)).thenThrow(exception)
        // 2. Act
        val results = repository.getSearchRecipes(queryMap).toList()
        // 3. Assert
        // Flow emission logic in Repo:
        // 1. emit(Resource.Loading())
        // 2. catch(e) -> emit(Resource.Error())
        assertEquals(2, results.size) // Loading, then Error
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Error)
        assertEquals(errorMessage, results[1].message)
    }

    @Test
    fun `getSearchRecipes returns Error when IOException occurs`() = runTest {
        val queryMap = searchQuery()

        // Simulate Network Failure (No Internet)
        `when`(api.searchRecipes(queryMap)).thenAnswer {
            throw IOException("Couldn't reach server, check your internet connection.")
        }

        val results = repository.getSearchRecipes(queryMap).toList()

        // In your repo, IOException returns: "Couldn't reach server..."
        assertTrue(results.last() is Resource.Error)
        assertEquals(
            "Couldn't reach server, check your internet connection.",
            results.last().message
        )
    }

    @Test
    fun `getSearchRecipes return isSuccessful as false`() = runTest {
        val queryMap = searchQuery()
        // Simulate Network Failure (No Internet)
        `when`(api.searchRecipes(queryMap))
            .thenReturn(
                Response.error(
                    404,
                    ResponseBody.create(null,"")
                )
            )
        val results = repository.getSearchRecipes(queryMap).toList()
        assertTrue(results.last() is Resource.Error)
        assertEquals("The requested resource was not found.",results.last().message)
    }
    @Test
    fun `testBackend will throw exception`() = runTest{
        val queryMap = searchQuery()
        `when`(api.searchRecipes(queryMap))
            .thenThrow(RuntimeException("Backend error"))
        val results = repository.getSearchRecipes(queryMap).toList()
        assertTrue(results.last() is Resource.Error)
        assertEquals("Oops, something went wrong!",results.last().message)
    }

    @Test
    fun `getFood Jokes returns Success with data from DB` () = runTest {

        val apiResponse = getFoodJokeResponseDto()
        val queryMap = getFoodJokeQuery()
        val foodJokeEntity = getFoodEntity()


        `when`(foodJokeDao.readFoodJoke())
            .thenReturn(listOf())
            .thenReturn(listOf(foodJokeEntity))


        `when`(api.getFoodJoke(queryMap))
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

    fun getFoodJokeResponse(): FoodJoke {
        return FoodJoke(
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
        quries["type"] = "main course"       // "main course"
        quries["diet"] = "gluten free"               //"gluten free"
        quries["addRecipeInformation"] = "true"
        quries["fillIngredients"] = "true"

        return quries
    }

    fun getFoodJokeQuery(): String{
        val quries: String = "4b1d5ec4278045d2a16c8bf467004700"
        return quries
    }

}