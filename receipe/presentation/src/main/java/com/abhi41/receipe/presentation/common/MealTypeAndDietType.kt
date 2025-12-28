package com.abhi41.receipe.presentation.common

import com.abhi41.receipe.presentation.utils.Constants.DEFAULT_DIET_TYPE
import com.abhi41.receipe.presentation.utils.Constants.DEFAULT_MEAL_TYPE

class MealType {
    companion object {
        fun getMeals() = listOf<Meal>(
            Meal(DEFAULT_MEAL_TYPE),
            Meal("Side Dish"),
            Meal("Dessert"),
            Meal("Appetizer"),
            Meal("Salad"),
            Meal("Soup"),
            Meal("Sauce"),
            Meal("Drink"),
        )
    }
}

class DietType{
    companion object{
        fun getDiets() = listOf<Diet>(
            Diet(DEFAULT_DIET_TYPE),
            Diet("Ketogenic"),
            Diet("Vegetarian"),
            Diet("Vegan"),
            Diet("Paleo"),
            Diet("Low FODMAP"),
            Diet("Pescetarian"),
            Diet("Primal"),
            Diet("Whole30"),
        )
    }
}

data class Meal(
    var meal: String
)

data class Diet(
    var diet: String
)