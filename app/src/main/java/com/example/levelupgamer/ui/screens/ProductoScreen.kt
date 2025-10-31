// ui.screens/ProductoScreen.kt

package com.example.levelupgamer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamer.logic.GameStoreViewModel
import com.example.levelupgamer.data.listaDeJuegos
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.levelupgamer.data.Juego

// ⚠️ CORRECCIÓN CLAVE: Añadir la anotación para permitir el uso de funciones experimentales
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoScreen(
    navController: NavController,
    viewModel: GameStoreViewModel,
    juegoId: String // Recibido del NavController
) {
    // 1. Obtener los datos del juego y el estado
    val juego = listaDeJuegos.find { it.idJuego == juegoId }
    val estaEnBiblioteca = viewModel.estaEnBiblioteca(juegoId)

    // Si el juego no se encuentra (caso de error), vuelve a Home
    if (juego == null) {
        navController.popBackStack()
        return
    }

    // 2. Definir el contenido de la pantalla
    Scaffold(
        topBar = { ProductoTopBar(navController) },
        bottomBar = {
            BotonAccion(
                juego = juego,
                estaComprado = estaEnBiblioteca,
                onAccionClick = {
                    if (!estaEnBiblioteca) {
                        // Simulación de compra y actualización de estado
                        viewModel.agregarJuegoABiblioteca(juegoId)
                    }
                }
            )
        }
    ) { innerPadding ->
        DetalleProducto(juego, innerPadding)
    }
}

@Composable
fun BotonAccion(
    juego: Juego,
    estaComprado: Boolean,
    onAccionClick: () -> Unit
) {
    Surface(shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Precio o Estado
            Text(
                text = if (estaComprado) "¡En tu Biblioteca!" else "$${juego.precio}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = if (estaComprado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error // Indicador visual de compra
            )

            // Botón de Acción
            Button(
                onClick = onAccionClick,
                enabled = !estaComprado, // Deshabilita si ya está comprado
                modifier = Modifier.width(150.dp)
            ) {
                Text(if (estaComprado) "Jugar" else "Comprar")
            }
        }
    }
}

// ⚠️ CORRECCIÓN CLAVE: Aplicar la anotación aquí también y usar AssistChip
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProducto(juego: Juego, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        // IMAGEN DEL JUEGO: Elemento clave para la Animación Hero
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                // Usamos .semantics para dar un identificador único (simulación de Hero ID)
                .semantics { contentDescription = "imagen_heroe_${juego.idJuego}" }
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "IMG_HERO",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = juego.titulo,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))

            // CORRECCIÓN: Reemplazar el antiguo 'Chip' por AssistChip
            AssistChip(
                onClick = { /* No-op */ },
                label = { Text(juego.categoria) }
            )
            Spacer(Modifier.height(16.dp))

            // Descripción larga (simulada)
            Text(
                text = "Esta es la descripción detallada de ${juego.titulo}. El juego ofrece gráficos impresionantes y una historia envolvente...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

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