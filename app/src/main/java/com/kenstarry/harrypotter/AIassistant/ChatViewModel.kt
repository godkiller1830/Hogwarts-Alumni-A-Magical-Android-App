package com.kenstarry.harrypotter.AIassistant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel(private val chatService: ChatGPTService) : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    // Utility function to create the prompt for the technical support request
    private fun getTechnicalSupportPrompt(userIssue: String): String {
        return """
            Welcome to Hogwarts Alumni, the enchanted portal for all things Harry Potter. Here you can discover the secrets of Hogwarts, delve into the details of your favorite spells, and explore the depths of wizarding history.

        Question: $userIssue

        Remember, whether it's about the bravest deeds of Gryffindor, the clever contraptions of Ravenclaw, the ambitious plans of Slytherin, or the loyal hearts of Hufflepuff, no detail is too small in the vast world of Harry Potter. Ask away!
            
        """.trimIndent()
    }

    fun sendMessage(content: String) {
        val currentMessages = _messages.value
        // Update messages with the new message from the user
        _messages.value = currentMessages + Message("user", content)

        viewModelScope.launch {
            try {
                val response = chatService.getResponse(ChatRequest(messages = listOf(Message("user", content))))
                response.choices.firstOrNull()?.message?.let {
                    val updatedMessages = _messages.value + it
                    _messages.value = updatedMessages
                }

            } catch (e: Exception) {
                // Add logging here to see the error
                Log.e("ChatViewModel", "Error sending message", e)
            }

        }
    }
    fun sendSupportMessage(userIssue: String) {
        // Update the UI immediately with the user's issue
        val currentMessages = _messages.value + Message("user", userIssue)
        _messages.value = currentMessages

        val prompt = getTechnicalSupportPrompt(userIssue)

        viewModelScope.launch {
            try {
                // Create the request payload with the prompt
                val requestPayload = ChatRequest(messages = listOf(Message("assistant", prompt)))
                val response = chatService.getResponse(requestPayload)
                // Update the UI with the GPT-3 response
                response.choices.firstOrNull()?.message?.let {
                    _messages.value = currentMessages + it
                }
            } catch (e: Exception) {
                // Log the error, you could also update the UI to inform the user of an error
                Log.e("ChatViewModel", "Error sending support message", e)
                // Handle error state in UI, e.g., show an error message
            }
        }
    }
}
