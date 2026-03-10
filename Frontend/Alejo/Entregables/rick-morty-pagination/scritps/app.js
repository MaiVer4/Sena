// app.js
// Importa funciones de la API, UI y paginación
import { getCharacters } from "./api.js";
import { renderCharacters, showLoading, hideLoading, updatePageIndicator, showError } from "./ui.js";
import { pagination, nextPage, prevPage, firstPage, lastPage, jumpForward, jumpBackward } from "./pagination.js";

/**
 * Carga los personajes de la API según la página actual.
 * Muestra indicador de carga, renderiza personajes y actualiza la paginación.
 */
async function loadCharacters() {
    try {
        showLoading();
        const data = await getCharacters(pagination.currentPage);

        // Verifica si hay resultados válidos
        if (!data || !data.results) {
            showError("No se encontraron personajes.");
            hideLoading();
            return;
        }

        renderCharacters(data.results);
        pagination.totalPages = data.info.pages;

        updatePageIndicator(pagination.currentPage, pagination.totalPages);
        hideLoading();
    } catch (error) {
        showError("¡Error al conectar con la API!");
        hideLoading();
    }
}

// --- Control de navegación (Eventos) ---
// Botón siguiente página
document.getElementById("next").addEventListener("click", () => {
    nextPage();
    loadCharacters();
});

// Botón página anterior
document.getElementById("prev").addEventListener("click", () => {
    prevPage();
    loadCharacters();
});

// Botón primera página
document.getElementById("first").addEventListener("click", () => {
    firstPage();
    loadCharacters();
});

// Botón última página
document.getElementById("last").addEventListener("click", () => {
    lastPage();
    loadCharacters();
});

// Botón avanzar 5 páginas
document.getElementById("next5").addEventListener("click", () => {
    jumpForward(5);
    loadCharacters();
});

// Botón retroceder 5 páginas
document.getElementById("prev5").addEventListener("click", () => {
    jumpBackward(5);
    loadCharacters();
});

// Carga inicial de personajes al abrir la página
loadCharacters();