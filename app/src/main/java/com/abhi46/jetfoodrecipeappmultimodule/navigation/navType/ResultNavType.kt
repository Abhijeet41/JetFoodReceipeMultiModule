package com.abhi46.jetfoodrecipeappmultimodule.navigation.navType

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.abhi46.receipe.domain.models.RecipeResult
import kotlinx.serialization.json.Json

object ResultNavType : NavType<RecipeResult>(
    isNullableAllowed = false
) {

    override fun serializeAsValue(value: RecipeResult): String {
        return Uri.encode(Json.encodeToString(value))
    }
    override fun put(
        bundle: SavedState,
        key: String,
        value: RecipeResult
    ) {
        bundle.putString(
            key,
            Json.encodeToString(value)
        )
    }

    override fun get(
        bundle: SavedState,
        key: String
    ): RecipeResult? {
        return bundle.getString(key)?.let {
            Json.decodeFromString(bundle.getString(key) ?: return null)
        }
    }

    override fun parseValue(value: String): RecipeResult {
        return Json.decodeFromString(Uri.decode(value))
    }
}