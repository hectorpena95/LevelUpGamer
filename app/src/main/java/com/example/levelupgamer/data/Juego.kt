// data/Juego.kt

package com.example.levelupgamer.data

// Modelo de Datos para un Juego
data class Juego(
    val idJuego: String,
    val titulo: String,
    val descripcionCorta: String,
    // ⚠️ CAMBIO 1: SE AGREGA descripcionLarga
    val descripcionLarga: String,
    val categoria: String,
    val precio: Int,
    val imagenUrl: String
)

// Datos Estáticos de la Tienda (simulación con precios en Pesos Chilenos)
val listaDeJuegos = listOf(
    Juego(
        idJuego = "001",
        titulo = "Cyberpunk Neo",
        descripcionCorta = "Un RPG futurista de mundo abierto con neones y tecnología.",
        // ⚠️ CAMBIO 2: VALOR ASIGNADO
        descripcionLarga = "Sumérgete en el vasto mundo abierto de Neo-Kyoto. Personaliza tu personaje cibernético, elige tu camino entre corporaciones y pandillas, y descubre el misterio detrás del apagón digital. Más de 100 horas de contenido épico.",
        categoria = "RPG",
        precio = 49990,
        imagenUrl = "url_cyberpunk_neo"
    ),
    Juego(
        idJuego = "002",
        titulo = "Aetheria",
        descripcionCorta = "Aventura de fantasía épica con dragones y magia ancestral.",
        // ⚠️ CAMBIO 2: VALOR ASIGNADO
        descripcionLarga = "Viaja a través del continente de Eldoria, asolado por la guerra. Reúne a tu equipo, domina los cinco elementos de la magia y lucha contra el Señor Oscuro que busca sumir al mundo en la noche eterna. Gráficos de última generación y banda sonora orquestal.",
        categoria = "Fantasía",
        precio = 34990,
        imagenUrl = "url_aetheria"
    ),
    Juego(
        idJuego = "003",
        titulo = "Space Race 7",
        descripcionCorta = "Juego de carreras arcade a través de galaxias distantes.",
        // ⚠️ CAMBIO 2: VALOR ASIGNADO
        descripcionLarga = "Compite a velocidades supersónicas en circuitos intergalácticos llenos de peligros y atajos secretos. Desbloquea naves, mejora tus motores y conviértete en el campeón de la Copa Cósmica. Modo multijugador online incluido.",
        categoria = "Carreras",
        precio = 19990,
        imagenUrl = "url_space_race"
    ),
    Juego(
        idJuego = "004",
        titulo = "Zombies Inc.",
        descripcionCorta = "Estrategia y supervivencia contra hordas interminables de muertos.",
        // ⚠️ CAMBIO 2: VALOR ASIGNADO
        descripcionLarga = "Construye y gestiona tu base mientras coordinas a tus sobrevivientes contra el apocalipsis zombie. Investiga nuevas tecnologías y estrategias de defensa para sobrevivir a la noche. Cada decisión cuenta en este juego de estrategia en tiempo real.",
        categoria = "Estrategia",
        precio = 24990,
        imagenUrl = "url_zombies"
    )
)