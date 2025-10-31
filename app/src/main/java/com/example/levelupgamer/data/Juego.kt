// data/Juego.kt

package com.example.levelupgamer.data

// Modelo de Datos para un Juego
data class Juego(
    val idJuego: String,
    val titulo: String,
    val descripcionCorta: String,
    val categoria: String,
    val precio: Int, // Cambiamos a Int para manejar precios enteros grandes
    val imagenUrl: String
)

// Datos Estáticos de la Tienda (simulación con precios en Pesos Chilenos)
val listaDeJuegos = listOf(
    Juego(
        idJuego = "001",
        titulo = "Cyberpunk Neo",
        descripcionCorta = "Un RPG futurista de mundo abierto con neones y tecnología.",
        categoria = "RPG",
        precio = 49990, // $49.990 CLP
        imagenUrl = "url_cyberpunk_neo"
    ),
    Juego(
        idJuego = "002",
        titulo = "Aetheria",
        descripcionCorta = "Aventura de fantasía épica con dragones y magia ancestral.",
        categoria = "Fantasía",
        precio = 34990, // $34.990 CLP
        imagenUrl = "url_aetheria"
    ),
    Juego(
        idJuego = "003",
        titulo = "Space Race 7",
        descripcionCorta = "Juego de carreras arcade a través de galaxias distantes.",
        categoria = "Carreras",
        precio = 19990, // $19.990 CLP
        imagenUrl = "url_space_race"
    ),
    Juego(
        idJuego = "004",
        titulo = "Zombies Inc.",
        descripcionCorta = "Estrategia y supervivencia contra hordas interminables de muertos.",
        categoria = "Estrategia",
        precio = 24990, // $24.990 CLP
        imagenUrl = "url_zombies"
    )
)