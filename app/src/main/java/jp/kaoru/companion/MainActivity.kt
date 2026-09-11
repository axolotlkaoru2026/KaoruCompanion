package jp.kaoru.companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CompanionApp() }
    }
}

@Composable
fun CompanionApp() {
    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf("こんにちは、KAORUさん。")
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Kaoru Companion") }
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = input,
                        onValueChange = { input = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("メッセージ") }
                    )

                    Spacer(Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (input.isNotBlank()) {
                                messages.add(input.trim())
                                messages.add(
                                    "受け取りました。ここから会話機能を拡張できます。"
                                )
                                input = ""
                            }
                        }
                    ) {
                        Text("送信")
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) {
                    Text(it)
                }
            }
        }
    }
}
