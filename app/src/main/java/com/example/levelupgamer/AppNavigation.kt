package com.example.levelupgamer.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.ui.screens.HomeScreen
import com.example.levelupgamer.ui.screens.ProductoScreen

// 1. Definición de las Rutas de Navegación (Solo las del Problema 1)
object Routes {
    const val HOME = "home"
    const val PRODUCTO = "producto/{juegoId}"
    const val BIBLIOTECA = "biblioteca"
    // NOTA: Se ha eliminado la constante AJUSTES
}

@Composable
fun AppNavigation(viewModel: GameStoreViewModel) {
    val navController = rememberNavController()

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
    }
}