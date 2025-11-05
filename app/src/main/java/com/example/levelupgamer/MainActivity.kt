package com.example.levelupgamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamer.data.PreferenciasRepo
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.logic.GameStoreViewModelFactory
import com.example.levelupgamer.ui.AppNavigation
import com.example.levelupgamer.ui.theme.LevelUpGamerTheme
import com.example.levelupgamer.utilities.GameNotificationManager
import com.example.levelupgamer.ui.Routes
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // LLAMADA PARA CREAR EL CANAL DE NOTIFICACIÓN
        GameNotificationManager.createNotificationChannel(this)

        // Inicializar Repositorio y Factory
        val preferenciasRepo = PreferenciasRepo(applicationContext)
        val viewModelFactory = GameStoreViewModelFactory(preferenciasRepo)

        setContent {
            LevelUpGamerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Crear el ViewModel
                    val viewModel: GameStoreViewModel = viewModel(factory = viewModelFactory)

                    // Iniciar la Navegación
                    AppNavigation(
                        viewModel = viewModel
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    LevelUpGamerTheme {
        Text("Level Up Gamer")
    }
}