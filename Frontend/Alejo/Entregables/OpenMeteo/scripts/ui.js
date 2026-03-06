/**
 * Módulo de interfaz de usuario (UI)
 * Responsable de renderizar los datos meteorológicos y gestionar la presentación visual
 * Implementa el patrón de separación de responsabilidades: lógica de negocio vs presentación
 */

/**
 * Traduce códigos WMO (World Meteorological Organization) a descripciones legibles en español
 * Los códigos WMO son estándares internacionales para clasificar condiciones meteorológicas
 * @param {number} code - Código WMO del clima (0-99)
 * @returns {string} Descripción en español del estado del clima
 * @see https://open-meteo.com/en/docs - Documentación de códigos WMO
 */
const getWeatherDescription = (code) => {
    // Mapeo de códigos WMO más comunes a descripciones en español
    const descriptions = {
        0: "Despejado",             
        1: "Principalmente despejado",  
        2: "Parcialmente nublado",
        3: "Nublado",             
        45: "Niebla",                 
        48: "Escarcha",                 
        51: "Llovizna ligera",             
        61: "Lluvia débil",                
        71: "Nieve leve",                  
        95: "Tormenta eléctrica"       
    };
    // Retornar descripción si existe, o texto genérico si el código no está mapeado
    return descriptions[code] || "Clima Variado";
};

/**
 * Renderiza la información meteorológica en el contenedor de resultados del DOM
 * Crea una estructura HTML con todos los datos del clima formateados
 * @param {string} cityName
 * @param {Object} weatherData 
 * @param {number} weatherData.temperature 
 * @param {number} weatherData.windspeed 
 * @param {string} weatherData.time 
 * @param {number} weatherData.weathercode
 * @param {string} country 
 * @param {boolean} show
 * @returns {void}
 */
/**
 * Alterna la visibilidad del spinner de carga y el contenedor de resultados
 * Muestra el spinner mientras se realizan las peticiones a las APIs
 * Oculta el spinner cuando se completan las peticiones (éxito o error)
 * @param {boolean} show - true para mostrar spinner, false para ocultarlo y mostrar resultados
 * @returns {void}
 * @example
 * toggleLoading(true);   // Muestra spinner durante la carga
 * toggleLoading(false);  // Oculta spinner y muestra resultado o error
 */
export const toggleLoading = (show) => {
    // Obtener referencias a los elementos del DOM que se van a manipular
    const spinner = document.getElementById("loading-spinner");      // Contenedor del spinner
    const container = document.getElementById("result-container");   // Contenedor de resultados

    // Lógica de visualización según el estado de carga
    if (show) {
        // Si show es true: mostrar spinner, ocultar contenedor de resultados
        spinner.style.display = "flex";           // Mostrar spinner con flexbox
        container.style.display = "none";         // Ocultar contenedor de resultados
    } else {
        // Si show es false: ocultar spinner (los resultados ya están en el contenedor)
        spinner.style.display = "none";           // Ocultar spinner
        // Nota: el contenedor de resultados se muestra automáticamente desde renderWeather() o renderError()
    }
}

export const renderWeather = (cityName, weatherData, country) => {
    // Obtener referencia al contenedor de resultados en el DOM
    const container = document.getElementById("result-container");

    // Extraer solo la hora del timestamp ISO (formato: HH:MM:SS)
    // Ejemplo: "2026-03-05T14:30:00" → "14:30:00"
    const timeOnly = weatherData.time.split("T")[1];

    // Construir HTML con template literals e inyectar en el DOM
    container.innerHTML = `
        <h2>${cityName}, ${country}</h2>
        <div class="weather-info">
            <p><strong>Estado:</strong> ${getWeatherDescription(weatherData.weathercode)}</p>
            <p><strong>Temperatura:</strong> ${weatherData.temperature} °C</p>
            <p><strong>Viento:</strong> ${weatherData.windspeed} km/h</p>
            <p><strong>Hora de medición:</strong> ${timeOnly}</p>
        </div>
    `;

    // Asegurar que el contenedor sea visible (puede estar oculto por CSS)
    container.style.display = "flex";
};

/**
 * Muestra un mensaje de error estilizado al usuario en el contenedor de resultados
 * Utilizado cuando hay problemas de red, ciudad no encontrada, o datos inválidos
 * @param {string} message - Mensaje de error a mostrar al usuario
 * @returns {void}
 * @example
 * renderError("Ciudad no encontrada!");
 * renderError("Error al obtener coordenadas");
 */
export const renderError = (message) =>  {
    // Obtener referencia al contenedor de resultados
    const container = document.getElementById("result-container");
    
    // Inyectar HTML con la tarjeta de error estilizada
    container.innerHTML = `
        <div class="error-card">
            <p style="color: #d9534f; font-weight: bold;">⚠️ Error</p>
            <p>${message}</p>
        </div>
    `;
    
    // Hacer visible el contenedor para mostrar el error
    container.style.display = "flex";
}