// MainActivity.kt
package com.example.levelupgamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme // Importación clave para el tema
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.data.PreferenciasRepo
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.logic.GameStoreViewModelFactory
import com.example.levelupgamer.ui.AppNavigation // Importación de la Navegación
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme // Importación del Tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el modo de pantalla completa moderno
        setContent {
            // Se lanza el Composible principal de la aplicación
            GameStoreApp()
        }
    }
}

// Composible de nivel superior para inicialización y estado
@Composable
fun GameStoreApp() {
    val context = LocalContext.current

    // 1. Inicializar el Repositorio de Persistencia (Almacenamiento Local)
    val repo = PreferenciasRepo(context)

    // 2. Inicializar el ViewModel (Gestión de Estado) usando la fábrica
    val viewModel: GameStoreViewModel = viewModel(
        factory = GameStoreViewModelFactory(repo)
    )

    // 3. Observar el estado de la UI (incluido el tema) para que la UI se actualice
    // 'by' asegura que el tema cambie automáticamente
    val uiState by viewModel.uiState.collectAsState()

    // 4. Determinar el tema a aplicar (Cumple Problema 3: Personalización)
    val useDarkTheme = when (uiState.temaActual) {
        "oscuro" -> true
        "claro" -> false
        // Si es 'sistema' o cualquier otro valor, se usa la preferencia del dispositivo
        else -> isSystemInDarkTheme()
    }

    // 5. Aplicar el tema M3 y lanzar la Navegación Funcional
    LevelUpGamerTheme(darkTheme = useDarkTheme) {
        // Pasamos el ViewModel a la navegación para que todas las pantallas accedan al estado
        AppNavigation(viewModel)
    }
}

// Las funciones Greeting y GreetingPreview ya no son necesarias y se han eliminado.