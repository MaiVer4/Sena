type Unidad = "cm" | "m" | "km";

const factores: Record<Unidad, number> = {
    cm: 0.01,
    m: 1,
    km: 1000
};

function convertir(
    valor: number,
    de: Unidad,
    a: Unidad
): number {
    if (valor < 0) {
        throw new Error("El valor no puede ser negativo");
    }
    const enMetros = valor * factores[de];
    return Number((enMetros / factores[a]).toFixed(2));
}

// Pruebas
console.log(convertir(100, "cm", "m")); // 1
console.log(convertir(2, "km", "m"));   // 2000
console.log(convertir(500, "m", "km")); // 0.5