package com.example.levelupgamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text // <--- ⚠️ CORRECCIÓN: Importación faltante
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.data.PreferenciasRepo
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.logic.GameStoreViewModelFactory
import com.example.levelupgamer.ui.AppNavigation
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Inicializar el Repositorio de Persistencia
        val preferenciasRepo = PreferenciasRepo(applicationContext)

        // 2. Definir la Fábrica del ViewModel
        val viewModelFactory = GameStoreViewModelFactory(preferenciasRepo)

        setContent {
            LevelUpGamerTheme {
                // Contenedor principal
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. Crear el ViewModel (solo se inicializa una vez)
                    val viewModel: GameStoreViewModel = viewModel(factory = viewModelFactory)

                    // 4. Iniciar la Navegación y pasar el estado
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    LevelUpGamerTheme {
        Text("Level Up Gamer") // Este Text ya no da error.
    }
}