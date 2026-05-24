package com.abhi46.core_network.dtos.food_joke

import com.google.gson.annotations.SerializedName

data class FoodJokeDto(
    @SerializedName("text")
    val text: String?
)
