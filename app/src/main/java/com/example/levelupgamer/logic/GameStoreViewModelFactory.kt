package com.example.levelupgamer.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupgamer.data.PreferenciasRepo


class GameStoreViewModelFactory(private val repo: PreferenciasRepo) : ViewModelProvider.Factory {

    // Sobreescribe el método para crear la instancia del ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Verifica que la clase solicitada sea GameStoreViewModel
        if (modelClass.isAssignableFrom(GameStoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // Retorna una nueva instancia de GameStoreViewModel con el repositorio inyectado
            return GameStoreViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}