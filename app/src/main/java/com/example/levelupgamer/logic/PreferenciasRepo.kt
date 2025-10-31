package com.example.levelupgamer.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Clave para DataStore
private val Context.dataStore by preferencesDataStore(name = "gamer_prefs")

// Clave única de Persistencia (solo la biblioteca)
private val BIBLIOTECA_KEY = stringSetPreferencesKey("biblioteca_ids")

class PreferenciasRepo(private val context: Context) {

    // CARGAR: Recupera el Set de IDs de juegos comprados
    suspend fun cargarBibliotecaIDs(): Set<String> {
        return context.dataStore.data
            .map { preferences ->
                preferences[BIBLIOTECA_KEY] ?: emptySet()
            }.first()
    }

    // GUARDAR: Persiste el Set de IDs de juegos comprados
    suspend fun guardarBibliotecaIDs(ids: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[BIBLIOTECA_KEY] = ids
        }
    }

    // NOTA: Se han eliminado las funciones cargarTema y guardarTema.
}