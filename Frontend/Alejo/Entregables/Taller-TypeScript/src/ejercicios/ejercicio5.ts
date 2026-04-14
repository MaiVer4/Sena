type Tipocambio = "nombre" | "correo" | "password";

interface CambiosUsuario {
    tipo: Tipocambio;
    fecha: Date;
}

type ResumenCambios = {
    nombre: number;
    correo: number;
    password: number;
}

function resumirCambios(cambios: CambiosUsuario[]): ResumenCambios {
    return cambios.reduce<ResumenCambios>((acc, cambio) => {
        acc[cambio.tipo]++;

        return acc;
    }, {
        nombre: 0,
        correo: 0,
        password: 0
    });
}

// Datos de prueba
const cambios: CambiosUsuario[] = [
  { tipo: "nombre", fecha: new Date() },
  { tipo: "correo", fecha: new Date() },
  { tipo: "password", fecha: new Date() },
  { tipo: "nombre", fecha: new Date() },
  { tipo: "password", fecha: new Date() },
  { tipo: "password", fecha: new Date() }
];

const resul = resumirCambios(cambios);
console.log(resul);
