package com.example.levelupgamer.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.PreferenciasRepo
import com.example.levelupgamer.data.Juego
import com.example.levelupgamer.data.listaDeJuegos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.levelupgamer.utilities.GameNotificationManager
import android.content.Context

data class GameStoreUiState(
    val juegosDisponibles: List<Juego> = listaDeJuegos,
    val idsEnBiblioteca: Set<String> = emptySet()
)

class GameStoreViewModel(private val repo: PreferenciasRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(GameStoreUiState())
    val uiState: StateFlow<GameStoreUiState> = _uiState

    init {
        cargarEstadoInicial()
    }

    private fun cargarEstadoInicial() {
        viewModelScope.launch {
            val ids = repo.cargarBibliotecaIDs()
            _uiState.update { currentState ->
                currentState.copy(
                    idsEnBiblioteca = ids
                )
            }
        }
    }

    fun estaEnBiblioteca(idJuego: String): Boolean {
        return _uiState.value.idsEnBiblioteca.contains(idJuego)
    }

    fun adquirirJuego(id: String, context: Context) {
        viewModelScope.launch {
            _uiState.update { currentState ->

                // 1. Crear nuevo set y guardar persistencia
                val nuevoSet = currentState.idsEnBiblioteca.toMutableSet().apply {
                    add(id)
                }

                repo.guardarBibliotecaIDs(nuevoSet)

                // 2. BUSCA EL TÍTULO DEL JUEGO Y LANZA LA NOTIFICACIÓN
                val tituloJuego = listaDeJuegos.find { it.idJuego == id }?.titulo ?: "Juego Desconocido"

                // ⭐️ CORRECCIÓN CLAVE: La función de notificación solo necesita el Context y el Título
                GameNotificationManager.showAcquisitionNotification(context, tituloJuego)

                // 3. Actualizar estado local
                currentState.copy(
                    idsEnBiblioteca = nuevoSet
                )
            }
        }
    }

    fun resetearBiblioteca() {
        viewModelScope.launch {
            _uiState.update { currentState ->

                // 1. Guardar un conjunto vacío en el DataStore (persistencia)
                repo.guardarBibliotecaIDs(emptySet())

                // 2. Actualizar el estado local
                currentState.copy(
                    idsEnBiblioteca = emptySet()
                )
            }
        }
    }
}