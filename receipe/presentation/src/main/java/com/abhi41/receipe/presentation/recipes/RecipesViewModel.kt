package com.abhi41.receipe.presentation.recipes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.abhi41.receipe.domain.useCases.GetAllRecipesUseCase
import com.abhi41.receipe.domain.utils.Resource
import com.abhi41.receipe.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.abhi41.receipe.domain.repository.AnalyticsTracker
import com.abhi41.receipe.domain.repository.PreferencesRepository
import com.abhi41.receipe.domain.utils.Diet
import com.abhi41.receipe.domain.utils.DietType
import com.abhi41.receipe.domain.utils.Meal
import com.abhi41.receipe.domain.utils.MealAndDietType
import com.abhi41.receipe.domain.utils.MealType
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RecipesViewModel"

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getAllRecipesUseCase: GetAllRecipesUseCase,
    private val prefRepo: PreferencesRepository,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    var selectedMealType = mutableStateOf(MealType.getMeals().get(0))
    var selectedDietType = mutableStateOf(DietType.getDiets().get(0))
    private val _recipesState = mutableStateOf(RecipesState())
    val recipesState: State<RecipesState> = _recipesState
    var readPreferences = prefRepo.mealAndDietFlow
    lateinit var mealAndDietType: MealAndDietType


    init {
        viewModelScope.launch {
            readPreferences.collect { state ->
                withContext(Dispatchers.Main) {
                    selectedMealType.value = Meal(state.selectedMealType)
                    selectedDietType.value = Diet(state.selectedDietType)
                    Log.d(TAG, ": Initiated RecipesViewModel")
                    getRecipes(
                        mealType = selectedMealType.value,
                        dietType = selectedDietType.value
                    )
                }
            }
        }
    }

    fun logRecipeClickedEvent(
        recipeId: String,
        recipeName: String,
        screenName: String
    ) {
        analyticsTracker.logEvent(
            eventName = "recipe_clicked",
            params = mapOf(
                "event_id" to recipeId,
                "recipe_name" to recipeName,
                "screen_name" to screenName,
            )
        )
    }

    fun trackScreen() {
        analyticsTracker.logEvent(
            eventName = FirebaseAnalytics.Event.SCREEN_VIEW, // Correct, standard event name
            params = mapOf(
                FirebaseAnalytics.Param.SCREEN_NAME to "RecipeListScreen", // Correct parameter name
                FirebaseAnalytics.Param.SCREEN_CLASS to "RecipesScreen"    // Good practice to include class/composable name
            )
        )
    }

    fun getRecipes(mealType: Meal, dietType: Diet) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllRecipesUseCase(
                queries = applyQuries(mealType.meal, dietType.diet)
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _recipesState.value = recipesState.value.copy(
                            recipesItem = result.data ?: emptyList(),
                            isLoading = false,
                            error = ""
                        )
                        saveMealAndDietType(mealType.meal, dietType.diet)
                    }

                    is Resource.Error -> {
                        _recipesState.value = recipesState.value.copy(
                            recipesItem = result.data ?: emptyList(),
                            isLoading = false,
                            error = result.message ?: "Unknow Error"
                        )
                    }

                    is Resource.Loading -> {
                        withContext(Dispatchers.Main) {
                            _recipesState.value = recipesState.value.copy(
                                recipesItem = result.data ?: emptyList(),
                                isLoading = true,
                                error = ""
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    private fun saveMealAndDietType(meal: String, diet: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mealAndDietType = MealAndDietType(
                meal, diet
            )
            prefRepo.saveMealAndDietType(mealAndDietType)
        }
    }

    fun applyQuries(
        mealType: String,
        dietType: String,
    ): HashMap<String, String> {
        val quries: HashMap<String, String> = HashMap()

        quries[Constants.QUERY_NUMBER] = "50"
        quries[Constants.QUERY_API_KEY] = Constants.API_KEY
        quries[Constants.QUERY_TYPE] = mealType       // "main course"
        quries[Constants.QUERY_DIET] = dietType               //"gluten free"
        quries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        quries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        Log.d("VM_mealType", mealType)
        Log.d("VM_dietType", dietType)

        return quries
    }
}