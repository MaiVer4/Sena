// pagination.js

/**
 * Objeto de paginación global.
 * currentPage: Página actual.
 * totalPages: Total de páginas disponibles.
 */
export const pagination = {
    currentPage: 1,
    totalPages: 1
}

/**
 * Avanza a la siguiente página si no es la última.
 */
export function nextPage() {
    if (pagination.currentPage < pagination.totalPages) {
        pagination.currentPage++;
    }
}

/**
 * Retrocede a la página anterior si no es la primera.
 */
export function prevPage() {
    if (pagination.currentPage > 1) {
        pagination.currentPage--;
    }
}

/**
 * Va a la primera página.
 */
export function firstPage() {
    pagination.currentPage = 1;
}

/**
 * Va a la última página.
 */
export function lastPage() {
    pagination.currentPage = pagination.totalPages;
}

/**
 * Avanza n páginas, sin exceder el total.
 * @param {number} n - Número de páginas a avanzar.
 */
export function jumpForward(n) {
    const newPage = pagination.currentPage + n;
    if (newPage <= pagination.totalPages) {
        pagination.currentPage = newPage;
    } else {
        pagination.currentPage = pagination.totalPages;
    }
}

/**
 * Retrocede n páginas, sin bajar de la primera.
 * @param {number} n - Número de páginas a retroceder.
 */
export function jumpBackward(n) {
    const newPage = pagination.currentPage - n;
    if (newPage >= 1) {
        pagination.currentPage = newPage;
    } else {
        pagination.currentPage = 1;
    }
}