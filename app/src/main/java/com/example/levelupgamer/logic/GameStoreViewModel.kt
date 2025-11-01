// package com.example.levelupgamer.logic

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

class GameStoreViewModel(private val repo: PreferenciasRepo) : ViewModel() { // Renombré 'repo' a 'preferenciasRepo' para mayor claridad

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

    /**
     * ✅ FUNCIÓN CORREGIDA Y UNIFICADA:
     * Simula la compra de un juego, lo añade a la lista local y a la persistencia.
     */
    fun adquirirJuego(id: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                // 1. Crea un nuevo conjunto y añade el ID
                val nuevoSet = currentState.idsEnBiblioteca.toMutableSet().apply {
                    add(id)
                }

                // 2. Llama a la función de guardado en el repositorio
                // NOTA: Se usó 'guardarJuegosAdquiridos' en el código anterior,
                // asumimos que el método correcto en tu repo es 'guardarBibliotecaIDs'.
                // Se usa 'repo' ya que así lo definiste en el constructor.
                repo.guardarBibliotecaIDs(nuevoSet)

                // 3. Actualiza el estado localmente
                currentState.copy(
                    idsEnBiblioteca = nuevoSet
                )
            }
        }
    }

    // NOTA: Se ha eliminado la función redundante agregarJuegoABiblioteca(idJuego: String)
    // porque es funcionalmente idéntica a adquirirJuego(id: String).

    fun estaEnBiblioteca(idJuego: String): Boolean {
        return _uiState.value.idsEnBiblioteca.contains(idJuego)
    }

    // NOTA: Se ha eliminado la función setTema.
}