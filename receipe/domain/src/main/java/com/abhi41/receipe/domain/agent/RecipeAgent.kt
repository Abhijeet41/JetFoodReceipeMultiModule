package com.abhi41.receipe.domain.agent

interface RecipeAgent {
    suspend fun getResponse(message: String): String
}
