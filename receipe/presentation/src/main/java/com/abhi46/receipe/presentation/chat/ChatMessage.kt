package com.abhi46.receipe.presentation.chat

data class ChatMessage(
    val message: String,
    val isUser: Boolean,
    val isTypingIndicator: Boolean = false
)
