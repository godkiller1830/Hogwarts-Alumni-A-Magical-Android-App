package com.kenstarry.harrypotter.AIassistant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            val retrofit = OpenAIService.retrofit
            val chatService = retrofit.create(ChatGPTService::class.java)
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(chatService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
