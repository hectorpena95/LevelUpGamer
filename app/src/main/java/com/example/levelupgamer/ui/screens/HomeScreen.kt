package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.data.Juego
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.ui.Routes
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GameStoreViewModel
) {
    // 1. Observar el Estado
    val uiState by viewModel.uiState.collectAsState()
    val juegos = uiState.juegosDisponibles

    Scaffold(
        topBar = { HomeTopBar(navController) }
    ) { innerPadding ->
        // 2. Lista de Juegos
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
                        // Navegación a la ficha de producto
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
            // Navegar a la Biblioteca
            IconButton(onClick = { navController.navigate(Routes.BIBLIOTECA) }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
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
            .height(150.dp) // ⭐️ Altura ajustada
            .clickable(onClick = onCardClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ⭐️ ZONA DE IMAGEN CON COIL (100dp) ⭐️
            AsyncImage(
                model = juego.imagenUrl,
                contentDescription = juego.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp) // Tamaño de la imagen ajustado a 100dp
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            )
            // ⭐️ FIN DE ZONA DE IMAGEN ⭐️

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Título (Ahora soporta 2 líneas)
                Text(
                    text = juego.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                // Descripción corta (Ahora soporta 2 líneas)
                Text(
                    text = juego.descripcionCorta,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Precio
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