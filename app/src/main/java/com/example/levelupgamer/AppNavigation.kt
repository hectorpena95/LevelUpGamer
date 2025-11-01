// ui/AppNavigation.kt

package com.example.levelupgamer.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController // ⚠️ Importación CLAVE: Necesaria para tipar el controlador
import androidx.navigation.NavType // ⚠️ Importación CLAVE: Necesaria para NavType
import androidx.navigation.compose.NavHost // ⚠️ Importación CLAVE: Necesaria para NavHost
import androidx.navigation.compose.composable // ⚠️ Importación CLAVE: Necesaria para composable
import androidx.navigation.compose.rememberNavController // ⚠️ Importación CLAVE: Necesaria para rememberNavController
import androidx.navigation.navArgument // ⚠️ Importación CLAVE: Necesaria para navArgument
import androidx.lifecycle.viewmodel.compose.viewModel // ⚠️ Importación CLAVE: Necesaria para usar viewModel en MainActivity

import com.example.levelupgamer.logic.GameStoreViewModel // Importación de tu ViewModel
import com.example.levelupgamer.ui.screens.BibliotecaScreen // ⚠️ Importación CLAVE: Necesaria para la nueva ruta
import com.example.levelupgamer.ui.screens.HomeScreen
import com.example.levelupgamer.ui.screens.ProductoScreen

// 1. Definición de las Rutas de Navegación (Solo las del Problema 1)
object Routes {
    const val HOME = "home"
    const val PRODUCTO = "producto/{juegoId}"
    const val BIBLIOTECA = "biblioteca" // Ruta de la biblioteca
}

@Composable
fun AppNavigation(viewModel: GameStoreViewModel) {
    // Definimos el NavController
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // RUTA 1: HOME - Lista de Juegos
        composable(Routes.HOME) {
            HomeScreen(navController, viewModel)
        }

        // RUTA 2: FICHA DE PRODUCTO
        composable(
            Routes.PRODUCTO,
            arguments = listOf(navArgument("juegoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val juegoId = backStackEntry.arguments?.getString("juegoId")
            if (juegoId != null) {
                ProductoScreen(navController, viewModel, juegoId)
            }
        }

        // ⚠️ RUTA 3: BIBLIOTECA (Nueva ruta del Problema 1)
        composable(Routes.BIBLIOTECA) {
            BibliotecaScreen(navController, viewModel)
        }
    }
}