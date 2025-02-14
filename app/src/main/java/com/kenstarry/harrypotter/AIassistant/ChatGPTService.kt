package com.kenstarry.harrypotter.AIassistant

import retrofit2.http.Body
import retrofit2.http.POST

interface ChatGPTService {
    @POST("v1/chat/completions")
    suspend fun getResponse(@Body payload: ChatRequest): ChatResponse
}

data class ChatRequest(val model: String = "gpt-3.5-turbo", val messages: List<Message>)
data class Message(val role: String, val content: String)
data class ChatResponse(val choices: List<Choice>)
data class Choice(val message: Message)
