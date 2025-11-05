// ui.screens/BibliotecaScreen.kt

package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.data.Juego
import com.example.levelupgamer.data.listaDeJuegos
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibliotecaScreen(
    navController: NavController,
    viewModel: GameStoreViewModel // El ViewModel es esencial
) {
    // 1. Observar el estado (Lista de IDs comprados)
    val uiState by viewModel.uiState.collectAsState()
    val idsEnBiblioteca = uiState.idsEnBiblioteca

    // 2. Filtrar los juegos: Obtener solo los objetos Juego cuyos IDs están en la Biblioteca
    val juegosEnBiblioteca = listaDeJuegos.filter { juego ->
        idsEnBiblioteca.contains(juego.idJuego)
    }

    Scaffold(
        topBar = { BibliotecaTopBar(navController) },
        // ⭐️ BOTÓN FLOTANTE AÑADIDO PARA RESETEAR ⭐️
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    // ✅ Llama a la función de reseteo del ViewModel
                    viewModel.resetearBiblioteca()
                },
                icon = {
                    Icon(
                        Icons.Default.Refresh, // Ícono de refresco
                        contentDescription = "Resetear biblioteca"
                    )
                },
                text = { Text("RESET BIBLIOTECA") }
            )
        }
        // ⭐️ FIN DEL BOTÓN FLOTANTE ⭐️
    ) { innerPadding ->
        if (juegosEnBiblioteca.isEmpty()) {
            // Estado vacío: Si no hay juegos comprados
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tu biblioteca está vacía.\n¡Ve a la tienda a comprar algo!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Lista de Juegos Comprados
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 8.dp)
            ) {
                items(juegosEnBiblioteca) { juego ->
                    BibliotecaCard(juego = juego)
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

// ⚠️ OptIn necesario para ElevatedCard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibliotecaCard(juego: Juego) {
    ElevatedCard( // Componente que puede ser experimental.
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = juego.imagenUrl,
                contentDescription = juego.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
            )
            // ⭐️ FIN DE ZONA DE IMAGEN ⭐️

            // Título del Juego
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = juego.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = juego.categoria,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BibliotecaTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = { Text("Mi Biblioteca", fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        }
    )
}