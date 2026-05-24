package com.abhi46.receipe.domain.agent

interface RecipeAgent {
    suspend fun getResponse(message: String): String
}
