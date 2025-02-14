package com.kenstarry.harrypotter.AIassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    // Assuming you have a ChatViewModelFactory to create your ViewModel
                    val chatViewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory())
                    ChatScreen(chatViewModel)
                }
            }
        }
    }
}

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages = viewModel.messages.collectAsState().value
    val (isTechnicalSupport, setIsTechnicalSupport) = remember { mutableStateOf(false) }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        messages.forEach { message ->
            Text(text = "${message.role}: ${message.content}", style = MaterialTheme.typography.bodyMedium)
        }
        UserInputField(onSend = { message ->
            viewModel.sendSupportMessage(message)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInputField(onSend: (String) -> Unit) {
    val inputText = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        TextField(
            value = inputText.value,
            onValueChange = { inputText.value = it },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            if (inputText.value.isNotBlank()) {
                onSend(inputText.value)
                inputText.value = ""
            }
        }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Send")
        }
    }
}