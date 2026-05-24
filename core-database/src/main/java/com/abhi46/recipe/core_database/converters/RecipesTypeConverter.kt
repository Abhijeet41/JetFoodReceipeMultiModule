package com.abhi46.recipe.core_database.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.abhi46.recipe.core_database.entity.ExtendedIngredient
import com.abhi46.recipe.core_database.utils.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class RecipesTypeConverter(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromIngredientsJson(json: String): List<ExtendedIngredient> {
        return jsonParser.fromJson<ArrayList<ExtendedIngredient>>(
            json = json,
            type = object : TypeToken<ArrayList<ExtendedIngredient>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toIngredientsJson(ingredients: List<ExtendedIngredient>): String {
        return jsonParser.toJson(
            obj = ingredients,
            type = object : TypeToken<ArrayList<ExtendedIngredient>>() {}.type
        ) ?: "[]"
    }



}