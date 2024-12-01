package com.example.scoreantifraude

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.scoreantifraude.ui.theme.ScoreAntifraudeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScoreAntifraudeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    FraudScoreScreen()
                }
            }
        }
    }
}

@Composable
fun FraudScoreScreen() {
    var cpf by remember { mutableStateOf("") }
    var score by remember { mutableStateOf<Int?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Score Antifraude", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para CPF
        OutlinedTextField(
            value = cpf,
            onValueChange = { input ->
                if (input.length <= 11) { // CPF possui no máximo 11 dígitos
                    cpf = input.filter { it.isDigit() }
                    errorMessage = ""
                } else {
                    errorMessage = "CPF inválido"
                }
            },
            label = { Text("Digite seu CPF") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorMessage.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )

        // Exibição de mensagem de erro
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão para calcular o score
        Button(
            onClick = {
                if (cpf.length == 11) {
                    score = Random.nextInt(1, 1001) // Gera um score entre 1 e 1000
                } else {
                    errorMessage = "CPF deve conter 11 dígitos"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Score")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Exibição do resultado
        score?.let {
            Text(
                text = "Seu Score Antifraude é: $it",
                style = MaterialTheme.typography.titleMedium,
                color = if (it > 700) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
        }
    }
}
