package com.example.levelupgamer.ui

import androidx.compose.runtime.Composable
// ⚠️ NO NECESITAS: import android.app.Activity
// ⚠️ NO NECESITAS: import androidx.compose.runtime.LaunchedEffect
// ⚠️ NO NECESITAS: import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.ui.screens.BibliotecaScreen
import com.example.levelupgamer.ui.screens.HomeScreen
import com.example.levelupgamer.ui.screens.ProductoScreen
// ⚠️ NO NECESITAS: import com.example.levelupgamer.utilities.KEY_NAVIGATE_TO


// 1. Definición de las Rutas de Navegación
object Routes {
    const val HOME = "home"
    const val PRODUCTO = "producto/{juegoId}"
    const val BIBLIOTECA = "biblioteca"
}

@Composable
fun AppNavigation(
    viewModel: GameStoreViewModel
) {
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

        // RUTA 3: BIBLIOTECA
        composable(Routes.BIBLIOTECA) {
            BibliotecaScreen(navController, viewModel)
        }
    }
}