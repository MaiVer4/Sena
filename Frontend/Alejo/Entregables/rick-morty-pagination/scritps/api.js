/**
 * Obtiene personajes de la API de Rick and Morty según la página indicada.
 * @param {number} page - Número de página a consultar.
 * @returns {Promise<Object>} Datos de personajes o objeto vacío en caso de error.
 */
export async function getCharacters(page) {
    const url = `https://rickandmortyapi.com/api/character?page=${page}`;
    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error("Error en la API");
        const data = await response.json();
        return data;
    } catch (error) {
        // Devuelve objeto vacío si ocurre un error
        return {};
    }
}