// ui.screens/HomeScreen.kt

package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
// ⚠️ Corregido: Usamos el ícono de Menu para la Biblioteca
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.data.Juego
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.ui.Routes

// ⚠️ OptIn necesario para ElevatedCard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GameStoreViewModel
) {
    // 1. Observar el Estado (Gestión de Estado)
    val uiState by viewModel.uiState.collectAsState()
    val juegos = uiState.juegosDisponibles

    // 2. Scaffold: Estructura base con Barra Superior
    Scaffold(
        topBar = { HomeTopBar(navController) }
    ) { innerPadding ->
        // 3. Lista de Juegos
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(juegos) { juego ->
                JuegoCard(
                    juego = juego,
                    onCardClick = {
                        // Navegación correcta a la ficha de producto
                        navController.navigate("${Routes.PRODUCTO.substringBefore("/")}/${juego.idJuego}")
                    }
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

// Componente de Barra Superior (TopAppBar)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text("LEVEL UP GAMER", fontWeight = FontWeight.Bold)
        },
        actions = {
            // ⚠️ CORRECCIÓN CLAVE: Navegar a la BIBLIOTECA, eliminando la referencia a AJUSTES.
            IconButton(onClick = { navController.navigate(Routes.BIBLIOTECA) }) {
                Icon(
                    imageVector = Icons.Filled.Menu, // Usamos Menu como ícono de biblioteca
                    contentDescription = "Ir a Biblioteca"
                )
            }
        }
    )
}

// ⚠️ OptIn necesario para ElevatedCard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoCard(
    juego: Juego,
    onCardClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onCardClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // IMAGEN SIMULADA
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "IMG",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp, horizontal = 4.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = juego.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = juego.descripcionCorta,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Precio en Pesos Chilenos (CLP)
            Text(
                text = "$${juego.precio}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}