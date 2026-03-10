// ui.js

// Referencias a elementos del DOM
const charactersContainer = document.getElementById("characters");
const pageIndicator = document.getElementById("page-indicator");
const loading = document.getElementById("loanding");

/**
 * Muestra el indicador de carga.
 */
export function showLoading() {
    loading.style.display = "block";
}

/**
 * Oculta el indicador de carga.
 */
export function hideLoading() {
    loading.style.display = "none";
}

/**
 * Renderiza las tarjetas de personajes en el contenedor.
 * Muestra información adicional y color según el estado.
 * @param {Array} characters - Lista de personajes a mostrar.
 */
export function renderCharacters(characters) {
    charactersContainer.innerHTML = "";
    characters.forEach(character => {
        // Determinar un color según el estado
        const statusColor = character.status === 'Alive' ? '#a3e635' : character.status === 'Dead' ? '#ef4444' : '#9ca3af';

        const card = `
        <div class="character">
            <img src="${character.image}" alt="${character.name}">
            <div class="name">${character.name}</div>
            <div class="status" style="color: ${statusColor}">
                ● ${character.status} - ${character.species}
            </div>
            <div class="info-extra">
                <p><strong>Origen:</strong> ${character.origin.name}</p>
                <p><strong>Género:</strong> ${character.gender}</p>
                <p><strong>Apariciones:</strong> ${character.episode.length} episodios</p>
            </div>
        </div>
        `;
        charactersContainer.innerHTML += card;
    });
}

/**
 * Actualiza el indicador de página actual y total.
 * @param {number} currentPage - Página actual.
 * @param {number} totalPages - Total de páginas.
 */
export function updatePageIndicator(currentPage, totalPages) {
    pageIndicator.textContent = `Página ${currentPage} de ${totalPages}`;
}

/**
 * Muestra un mensaje de error en el contenedor de personajes.
 * @param {string} message - Mensaje a mostrar.
 */
export function showError(message) {
    charactersContainer.innerHTML = `
        <p style="color:red; text-align:center;">${message}</p>
    `;
}