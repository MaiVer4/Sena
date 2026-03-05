/**
 * Controlador principal de la aplicación de clima
 * Gestiona la interacción del usuario y coordina las llamadas a servicios y renderizado
 */

// Importar funciones de servicio para obtener datos de APIs externas
import { getCoordinates, getWeather } from "./service.js";
// Importar funciones de UI para manipular el DOM
import { renderWeather, renderError, toggleLoading } from "./ui.js";

// Obtener referencias a elementos del DOM que se usarán frecuentemente
const form = document.getElementById("weather-form");      // Formulario de búsqueda
const input = document.getElementById("cityName");         // Campo de entrada de texto

/**
 * Event Listener: Maneja el envío del formulario de búsqueda de clima
 * Proceso: captura ciudad → obtiene coordenadas → obtiene clima → renderiza resultado
 * @param {Event} e - Evento de submit del formulario
 */
form.addEventListener("submit", async (e) => {
    // Prevenir comportamiento por defecto del formulario (recargar página)
    e.preventDefault();

    // Obtener y limpiar el valor ingresado por el usuario
    const cityName = input.value.trim();

    try {
        // Validación: verificar que el campo no esté vacío
        if (!cityName) {
            throw new Error("Debe ingresar una ciudad")
        }

        toggleLoading(true);
        
        // Paso 1: Obtener coordenadas geográficas de la ciudad
        const cordinates = await getCoordinates(cityName);

        // Paso 2: Obtener datos meteorológicos usando las coordenadas obtenidas
        const weather = await getWeather(
            cordinates.latitude,
            cordinates.longitude
        )

        toggleLoading(false)
        // Paso 3: Delegar el renderizado de los datos a la capa de UI
        renderWeather (cordinates.name, weather, cordinates.country);
    } catch (error) {
        // Manejo de errores: delegar la visualización del error a la UI
        renderError(error.message);
    }
})