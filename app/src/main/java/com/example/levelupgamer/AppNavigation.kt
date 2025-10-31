package com.example.levelupgamer


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.ui.screens.HomeScreen
// Importaciones de las pantallas que crearemos:
import com.example.levelupgamer.ui.screens.ProductoScreen
import com.example.levelupgamer.ui.screens.SettingsScreen

// 1. Definición de las Rutas de Navegación (Constantes)
object Routes {
    const val HOME = "home"
    const val PRODUCTO = "producto/{juegoId}" // Ruta con argumento para Ficha de Producto
    const val BIBLIOTECA = "biblioteca" // Pendiente de implementación
    const val AJUSTES = "ajustes" // Para el Problema 3: Personalización
}

@Composable
fun AppNavigation(viewModel: GameStoreViewModel) {
    // 2. Controlador de Navegación (maneja la pila de pantallas)
    val navController = rememberNavController()

    // 3. Definición del Grafo de Navegación
    NavHost(
        navController = navController,
        startDestination = Routes.HOME // Pantalla de inicio
    ) {
        // RUTA 1: HOME - Lista de Juegos (Problema 1)
        composable(Routes.HOME) {
            HomeScreen(navController, viewModel)
        }

        // RUTA 2: FICHA DE PRODUCTO (Problema 1: Núcleo)
        // Recibe el ID del juego como argumento para cargar sus detalles.
        composable(
            Routes.PRODUCTO,
            arguments = listOf(navArgument("juegoId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Extrae el ID del juego de la ruta
            val juegoId = backStackEntry.arguments?.getString("juegoId")

            // Si el ID existe, carga la Ficha de Producto
            if (juegoId != null) {
                ProductoScreen(navController, viewModel, juegoId)
            }
        }

        // RUTA 3: BIBLIOTECA
        composable(Routes.BIBLIOTECA) {
            // BibliotecaScreen(navController, viewModel) // Pendiente de crear
        }

        // RUTA 4: AJUSTES (Problema 3: Personalización)
        composable(Routes.AJUSTES) {
            SettingsScreen(navController, viewModel) // Pantalla de ajustes de tema
        }
    }
}