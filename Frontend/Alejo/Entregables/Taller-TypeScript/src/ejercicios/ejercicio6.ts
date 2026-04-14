type Categoria = "tareas" | "quiz" | "examen";

interface Calificacion {
    estudianteID: number;
    materia: string;
    categoria: Categoria;
    nota: number;
}

type PromedioPorCategoria = Record<Categoria, number>;


function calcularPromedios( 
    calificaciones: Calificacion[],
    estudianteID: number,
): PromedioPorCategoria {

    // Filtramos por estudiante
    const filtradas = calificaciones.filter(
        c => c.estudianteID === estudianteID
    );

    //Acumuladores
    const acumulado: Record<Categoria, {suma: number; cantidad: number}> = {
        tareas: {suma: 0, cantidad: 0},
        quiz: {suma: 0, cantidad: 0},
        examen: {suma: 0, cantidad: 0}
    };

    //Recorrer y acumular
    filtradas.forEach(c => {
        acumulado[c.categoria].suma += c.nota;
        acumulado[c.categoria].cantidad++;
    });

    //Calcular Promedios
    const promedios: PromedioPorCategoria = {
        tareas: 0,
        quiz: 0,
        examen: 0
    };

    (Object.keys(acumulado) as Categoria[]).forEach(cat => {
        const {suma, cantidad } = acumulado[cat];
        promedios[cat] = cantidad > 0 ? suma / cantidad : 0;
    });
    return promedios;
}

// Datos de prueba
const data: Calificacion[] = [
  { estudianteID: 1, materia: "math", categoria: "tareas", nota: 4 },
  { estudianteID: 1, materia: "math", categoria: "tareas", nota: 3 },
  { estudianteID: 1, materia: "math", categoria: "quiz", nota: 3.5 },
  { estudianteID: 1, materia: "math", categoria: "examen", nota: 5 },
  { estudianteID: 2, materia: "math", categoria: "tareas", nota: 2 }
];

const resultado = calcularPromedios(data, 1);

console.log(resultado);