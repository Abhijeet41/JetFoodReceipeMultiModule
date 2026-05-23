package com.abhi41.core_network.gemini
import com.google.gson.annotations.SerializedName

data class GeminiRequest(
    @SerializedName("contents")
    val contents: List<Content>
)

data class Content(
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("parts")
    val parts: List<Part>
)

data class Part(
    @SerializedName("text")
    val text: String
)

data class GeminiResponse(
    @SerializedName("candidates")
    val candidates: List<Candidate>? = null,
    @SerializedName("error")
    val error: GeminiError? = null
)

data class Candidate(
    @SerializedName("content")
    val content: Content? = null
)

data class GeminiError(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("message")
    val message: String? = null
)
