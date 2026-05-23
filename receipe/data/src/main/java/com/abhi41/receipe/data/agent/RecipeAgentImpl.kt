package com.abhi41.receipe.data.agent

import com.abhi41.core_network.gemini.Content
import com.abhi41.core_network.gemini.GeminiApiService
import com.abhi41.core_network.gemini.GeminiRequest
import com.abhi41.core_network.gemini.Part
import com.abhi41.receipe.domain.agent.RecipeAgent
import retrofit2.HttpException
import javax.inject.Inject

class RecipeAgentImpl @Inject constructor(
    private val geminiApiService: GeminiApiService
) : RecipeAgent {
    // API key provided by user
    private val apiKey = "AIzaSyAgvu8FUyoWPIRwtGuTIBkpnXe7igDD-HQ"

    override suspend fun getResponse(message: String): String {
        return try {
            val request = GeminiRequest(
                contents = listOf(
                    Content(
                        role = "user",
                        parts = listOf(
                            Part(text = message)
                        )
                    )
                )
            )
            
            val response = geminiApiService.generateContent(apiKey, request)
            
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: response.error?.message
                ?: "I couldn't generate a response."
                
        } catch (e: HttpException) {
            "HTTP 400 Error: ${e.response()?.errorBody()?.string() ?: e.message}"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
