package com.example.levelupgamer.data

data class Juego(
    val idJuego: String,
    val titulo: String,
    val descripcionCorta: String,
    val descripcionLarga: String,
    val categoria: String,
    val precio: Int,
    val imagenUrl: String // Ahora contendrá una URL web
)

val listaDeJuegos = listOf(
    Juego(
        idJuego = "001",
        titulo = "Elden Ring",
        descripcionCorta = "Un épico RPG de acción y fantasía oscura en un vasto mundo abierto.",
        descripcionLarga = "Explora las Tierras Intermedias, un nuevo mundo de fantasía creado por Hidetaka Miyazaki y George R. R. Martin. Enfréntate a desafíos mortales, crea tu propio camino y conviértete en el Señor del Círculo. Ofrece combate profundo y personalización de personaje ilimitada.",
        categoria = "RPG de Acción",
        precio = 59990,
        // ✅ URL DIRECTA y verificada
        imagenUrl = "https://image.api.playstation.com/vulcan/ap/rnd/202110/2000/YMUoJUYNX0xWk6eTKuZLr5Iw.jpg"
    ),
    Juego(
        idJuego = "002",
        titulo = "Baldur's Gate 3",
        descripcionCorta = "El RPG táctico definitivo basado en Calabozos y Dragones.",
        descripcionLarga = "Reúne a tu grupo y regresa a los Reinos Olvidados. Experimenta una historia de compañerismo, traición, sacrificio, supervivencia y el atractivo del poder absoluto, forjando un destino épico con cada decisión. Incluye multijugador cooperativo de hasta 4 jugadores.",
        categoria = "RPG Táctico",
        precio = 45990,
        // ✅ URL DIRECTA y verificada
        imagenUrl = "https://image.api.playstation.com/vulcan/ap/rnd/202302/2321/3098481c9164bb5f33069b37e49fba1a572ea3b89971ee7b.jpg"
    ),
    Juego(
        idJuego = "003",
        titulo = "Hogwarts Legacy",
        descripcionCorta = "Aventura en el mundo de Harry Potter durante el siglo XIX.",
        descripcionLarga = "Vive la vida de un estudiante en Hogwarts, explora el mundo mágico de finales del siglo XIX. Haz amigos, lucha contra magos oscuros y descubre verdades ocultas del mundo mágico. Incluye vuelo en escoba, clases de hechizos y personalización de casa.",
        categoria = "Aventura",
        precio = 49990,
        // ✅ URL DIRECTA y verificada
        imagenUrl = "https://i.blogs.es/295b7f/hogwarts-legacy-desktop/1366_2000.jpeg"
    ),
    Juego(
        idJuego = "004",
        titulo = "Forza Horizon 5",
        descripcionCorta = "El festival de carreras definitivo ambientado en un vibrante México.",
        descripcionLarga = "Explora el mundo abierto y en constante evolución de México con cientos de los mejores autos del mundo. Compite en carreras extremas, realiza acrobacias y explora paisajes desérticos, selvas densas y ciudades históricas. Incluye clima dinámico y multijugador online.",
        categoria = "Carreras",
        precio = 39990,
        // ✅ URL DIRECTA y verificada
        imagenUrl = "https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/media/image/2021/08/forza-horizon-5-2448819.jpg"
    )
)