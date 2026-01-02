package com.abhi41.receipe.domain.repository

import com.abhi41.receipe.domain.utils.MealAndDietType
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val mealAndDietFlow: Flow<MealAndDietType>
    suspend fun saveMealAndDietType(mealAndDietType: MealAndDietType)
}