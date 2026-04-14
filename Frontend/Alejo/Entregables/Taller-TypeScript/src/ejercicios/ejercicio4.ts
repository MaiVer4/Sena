type TipoCampo = "texto" | "numero" | "email";

interface CampoFormulario {
    nombre: string;
    tipo: TipoCampo;
    valor: string | number;
}

type ResultadoValidacion = {
    nombre: string;
    valido: string;
};

function validarCampos(campos: CampoFormulario[]): ResultadoValidacion[] {
    const validadores = {
        texto: (v: any) => typeof v === "string" && v.trim() !== "",
        numero: (v: any) => typeof v === "number" && !isNaN(v),
        email: (v: any) => typeof v === "string" && v.includes("@")
    };

    return campos.map(campo => ({
        nombre: campo.nombre,
        valido: validadores[campo.tipo](campo.valor) ? "si" : "no"
    }));
}

// Prueba
const campos: CampoFormulario[] = [
    { nombre: "nombre", tipo: "texto", valor: "Maicol" },
    { nombre: "edad", tipo: "numero", valor: 20 },
    { nombre: "correo", tipo: "email", valor: "correo@gmail.com" },
    { nombre: "edad_invalida", tipo: "numero", valor: "abc" as any },
    { nombre: "correo_invalido", tipo: "email", valor: "sin-arroba.com" }
];

const resultado = validarCampos(campos);

console.log(resultado);