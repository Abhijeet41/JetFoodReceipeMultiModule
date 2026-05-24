package com.abhi46.receipe.domain.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val MEAL_TYPE = stringPreferencesKey(Constants.PREFERENCES_MEAL_TYPE)
    val DIET_TYPE = stringPreferencesKey(Constants.PREFERENCES_DIET_TYPE)
}