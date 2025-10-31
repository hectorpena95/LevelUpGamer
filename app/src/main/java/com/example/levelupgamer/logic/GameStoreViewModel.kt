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

// Estado simplificado: Solo necesitamos la lista de IDs comprados.
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
            // Solo cargamos los IDs de la biblioteca.
            val ids = repo.cargarBibliotecaIDs()

            _uiState.update { currentState ->
                currentState.copy(
                    idsEnBiblioteca = ids
                )
            }
        }
    }

    // ACCIÓN: Simulación de Compra
    fun agregarJuegoABiblioteca(idJuego: String) {
        viewModelScope.launch {
            val newIds = _uiState.value.idsEnBiblioteca.toMutableSet().apply {
                add(idJuego)
            }

            _uiState.update { it.copy(idsEnBiblioteca = newIds) }
            repo.guardarBibliotecaIDs(newIds) // Persistencia
        }
    }

    fun estaEnBiblioteca(idJuego: String): Boolean {
        return _uiState.value.idsEnBiblioteca.contains(idJuego)
    }

    // NOTA: Se ha eliminado la función setTema.
}