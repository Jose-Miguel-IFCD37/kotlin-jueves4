package com.visualstudioex3.jueves4

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaCronometro()
        }
    }
}

@Composable
fun PantallaCronometro() {
    // 1. Estados mutables para el valor del tiempo y el control del flujo
    var segundos by remember { mutableIntStateOf(0) }
    var estaCorriendo by remember { mutableStateOf(false) }

    // 2. El "Motor" del tiempo (LaunchedEffect)
    // Este bloque observa la variable 'estaCorriendo'. Si cambia a true, se ejecuta.
    LaunchedEffect(estaCorriendo) {
        while (estaCorriendo) {
            delay(1000L.milliseconds) // Espera exactamente 1 segundo (1000 milisegundos)
            segundos++   // Suma un segundo al contador
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 3. Visualización del tiempo
        Text(
            text = "$segundos segundos",
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 4. Fila de botones de control (Row para organizarlos en horizontal)
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // BOTÓN INICIAR
            Button(
                onClick = { estaCorriendo = true },
                // Opcional: Se deshabilita si ya está corriendo
                enabled = !estaCorriendo
            ) {
                Text(text = "Iniciar")
            }

            // BOTÓN PAUSAR
            Button(
                onClick = { estaCorriendo = false },
                // Opcional: Se deshabilita si ya está pausado
                enabled = estaCorriendo
            ) {
                Text(text = "Pausar")
            }

            // BOTÓN REINICIAR
            Button(
                onClick = {
                    estaCorriendo = false // Detiene el motor
                    segundos = 0          // Resetea el valor a cero
                }
            ) {
                Text(text = "Reiniciar")
            }
        }
    }
}
