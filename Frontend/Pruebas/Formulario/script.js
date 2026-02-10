document.addEventListener('DOMContentLoaded', async () => {
    const departamentoSelect = document.getElementById('departamento');
    const municipioSelect = document.getElementById('municipio');

    try {
        // Obtener departamentos
        const res = await fetch("https://api-colombia.com/api/v1/Department");
        const departamentos = await res.json();

        // Cargar departamentos
        departamentos.forEach(dep => {
            const option = document.createElement('option');
            option.value = dep.id; // ID del depto
            option.textContent = dep.name;
            departamentoSelect.appendChild(option);
        });

        // Cuando cambie el departamento
        departamentoSelect.addEventListener('change', async () => {
            municipioSelect.innerHTML = '<option value="">Seleccione un municipio</option>';

            const depId = departamentoSelect.value;
            if (!depId) return;

            try {
                // Pedir las ciudades de ese departamento
                const resCities = await fetch(`https://api-colombia.com/api/v1/Department/${depId}/cities`);
                const cities = await resCities.json();

                cities.forEach(city => {
                    const option = document.createElement('option');
                    option.value = city.id;
                    option.textContent = city.name;
                    municipioSelect.appendChild(option);
                });
            } catch (err) {
                console.error("Error cargando municipios:", err);
            }
        });

    } catch (error) {
        console.error("Error cargando departamentos:", error);
    }
});
 


