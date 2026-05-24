package com.abhi46.receipe.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.abhi46.receipe.domain.repository.PreferencesRepository
import com.abhi46.receipe.domain.utils.Constants
import com.abhi46.receipe.domain.utils.MealAndDietType
import com.abhi46.receipe.domain.utils.PreferencesKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCES_NAME
)


@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): PreferencesRepository{

    private val dataStore = context.dataStore

    override val mealAndDietFlow: Flow<MealAndDietType>
        get() = dataStore.data.catch { exeception->
            if (exeception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exeception
            }
        }.map { preferences ->
            MealAndDietType(
                selectedMealType = preferences[PreferencesKeys.MEAL_TYPE]
                    ?: Constants.DEFAULT_MEAL_TYPE,
                selectedDietType = preferences[PreferencesKeys.DIET_TYPE]
                    ?: Constants.DEFAULT_DIET_TYPE
            )
        }

    override suspend fun saveMealAndDietType(mealAndDietType: MealAndDietType) {
        dataStore.edit { prefs->
            prefs[PreferencesKeys.MEAL_TYPE] = mealAndDietType.selectedMealType
            prefs[PreferencesKeys.DIET_TYPE] = mealAndDietType.selectedDietType
        }
    }

}