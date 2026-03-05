/**
 * Servicio para interactuar con la API de Open-Meteo
 * Proporciona funciones para obtener coordenadas geográficas y datos meteorológicos
 */

/**
 * Obtiene las coordenadas geográficas de una ciudad utilizando la API de Geocoding de Open-Meteo
 * @async
 * @param {string} cityName - Nombre de la ciudad a buscar
 * @returns {Promise<{latitude: number, longitude: number, country: string, name: string}>} Objeto con coordenadas, país y nombre oficial
 * @throws {Error} Si la ciudad no se encuentra o hay un error en la petición
 * @example
 * const coords = await getCoordinates("Bogotá");
 * // Retorna: { latitude: 4.60971, longitude: -74.08175, country: "Colombia", name: "Bogotá" }
 */
export async function getCoordinates(cityName) {
    // Construir la URL con el parámetro de búsqueda y límite de resultados
    const url = `https://geocoding-api.open-meteo.com/v1/search?name=${cityName}&count=1`
    try {
        // Realizar petición HTTP GET a la API de geocodificación
        const response = await fetch(url);
        // Parsear la respuesta JSON
        const data  = await response.json();

        // Verificar que existan resultados en la respuesta
        if (data.results != null && data.results.length > 0) {
            // Retornar objeto con los datos geográficos del primer resultado
            return {
                latitude: data.results[0].latitude,
                longitude: data.results[0].longitude,
                country: data.results[0].country,
                name: data.results[0].name
            };
        } else {
            // Lanzar error si no hay resultados
            throw new Error("Ciudad no encontrada!")
        }
    } catch (error) {
        // Capturar y relanzar errores de red o parsing
        throw new Error("Error al obtener coordenadas")
    }
}

/**
 * Obtiene los datos meteorológicos actuales para unas coordenadas específicas
 * Utiliza la API de pronóstico de Open-Meteo para obtener información en tiempo real
 * @async
 * @param {number} latitude 
 * @param {number} longitude 
 * @returns {Promise<{temperature: number, windspeed: number, time: string, weathercode: number}>} Datos del clima actual
 * @throws {Error} Si no se pueden obtener los datos o hay un error en la petición
 * @example
 * const weather = await getWeather(4.60971, -74.08175);
 * // Retorna: { temperature: 18.5, windspeed: 12.3, time: "2026-03-05T14:30", weathercode: 2 }
 */
export async function getWeather(latitude, longitude) {
    // Construir URL con parámetros de coordenadas y solicitud de clima actual
    const url = `https://api.open-meteo.com/v1/forecast?latitude=${latitude}&longitude=${longitude}&current_weather=true`;
    try {
        // Realizar petición HTTP GET a la API meteorológica
        const response = await fetch(url);
        // Parsear respuesta JSON
        const data = await response.json();

        // Verificar que existan datos del clima actual en la respuesta
        if (data.current_weather != null) {
            // Retornar objeto con los datos meteorológicos relevantes
            return {
                temperature: data.current_weather.temperature,      // Temperatura en °C
                windspeed: data.current_weather.windspeed,          // Velocidad del viento en km/h
                time: data.current_weather.time,                    // Timestamp ISO 8601
                weathercode: data.current_weather.weathercode       // Código WMO del clima

            };
        } else {throw new Error("No se pudo obtener el clima");}
    } catch (error) {
        // Capturar y relanzar errores de red, parsing o datos inválidos
        throw new Error("Error al obtener el clima");
    }
}