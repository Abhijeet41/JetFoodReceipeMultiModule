package com.abhi41.receipe.presentation.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.receipe.domain.agent.RecipeAgent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val recipeAgent: RecipeAgent
) : ViewModel() {

    val messages = mutableStateListOf<ChatMessage>()
    
    init {
        // Initial greeting
        messages.add(
            ChatMessage(
                message = "Hello! I am your AI Recipe Assistant. How can I help you today?",
                isUser = false
            )
        )
    }

    fun sendMessage(input: String) {
        if (input.isBlank()) return
        
        // Add user message
        messages.add(ChatMessage(message = input.trim(), isUser = true))
        
        // Add typing indicator
        val typingMessage = ChatMessage(message = "AI is typing...", isUser = false, isTypingIndicator = true)
        messages.add(typingMessage)
        
        viewModelScope.launch {
            val response = recipeAgent.getResponse(input.trim())
            
            // Remove typing indicator and add final response
            messages.remove(typingMessage)
            messages.add(ChatMessage(message = response, isUser = false))
        }
    }
}
