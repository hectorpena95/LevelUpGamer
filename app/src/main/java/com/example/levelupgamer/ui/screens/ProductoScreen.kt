// ui.screens/ProductoScreen.kt

package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.data.listaDeJuegos
import com.example.levelupgamer.logic.GameStoreViewModel
import kotlinx.coroutines.launch

// ⚠️ OptIn necesario para TopAppBar y ElevatedCard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoScreen(
    navController: NavController,
    viewModel: GameStoreViewModel,
    gameId: String? // El ID que viene de la navegación
) {
    // 1. Obtener el estado y el juego
    val uiState by viewModel.uiState.collectAsState()
    val juego = remember(gameId) {
        listaDeJuegos.find { it.idJuego == gameId }
    }

    // 2. Controlar si el juego está en la biblioteca
    val yaAdquirido = uiState.idsEnBiblioteca.contains(gameId)

    // ******************************************************
    // 3. SNACKBAR HOST STATE (Nuevo)
    // ******************************************************
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (juego == null) {
        // Manejo básico si el juego no se encuentra
        Scaffold(
            topBar = { ProductoTopBar(navController) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: Juego no encontrado", style = MaterialTheme.typography.headlineSmall)
            }
        }
        return
    }

    Scaffold(
        topBar = { ProductoTopBar(navController) },
        // ******************************************************
        // 4. MOSTRAR SNACKBAR HOST (Nuevo)
        // ******************************************************
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            // Barra inferior con la lógica de compra/jugar
            BottomAppBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                // El botón que ejecuta la acción
                Button(
                    onClick = {
                        if (!yaAdquirido) {
                            viewModel.adquirirJuego(juego.idJuego)

                            // ******************************************************
                            // 5. MOSTRAR SNACKBAR AL COMPRAR (Nuevo)
                            // ******************************************************
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "¡Juego adquirido y añadido a tu Biblioteca!",
                                    actionLabel = "VER",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            // Simulación de acción "Jugar"
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Iniciando ${juego.titulo}...",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
                    // Deshabilitar la compra si ya lo tiene
                    enabled = true, // Siempre activo para mostrar el estado "Jugar"
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(50.dp)
                ) {
                    if (yaAdquirido) {
                        Text("JUGAR AHORA", fontWeight = FontWeight.Bold)
                    } else {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("COMPRAR - $${juego.precio}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        // Contenido de la Ficha de Producto
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = juego.titulo,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))

            // Categoría
            AssistChip( // Mejor que Chip para acciones
                onClick = { /* Acción */ },
                label = { Text(juego.categoria) }
            )
            Spacer(Modifier.height(16.dp))

            // Descripción Larga
            Text(
                text = juego.descripcionLarga,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))

            // Etiqueta de Adquirido (Si ya lo tiene)
            if (yaAdquirido) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "✔️ ¡Ya eres dueño de este juego!",
                        modifier = Modifier.padding(12.dp),
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

// Top Bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductoTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = { Text("Ficha de Producto") },
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