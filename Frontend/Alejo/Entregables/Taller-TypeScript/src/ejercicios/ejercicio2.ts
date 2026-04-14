 type TipoTransaccion = "ingreso" | "egreso";

interface Transaccion {
    monto: number;
    tipo: TipoTransaccion;
    categoria: string;
}

type ResumenTransaccion = {
    ingreso: number;
    egreso: number;
};

function agruparTransaccion(transacciones: Transaccion[]): ResumenTransaccion {
    return transacciones.reduce((acc, transaccion) => {

        if (transaccion.tipo === "ingreso") {
            acc.ingreso += transaccion.monto;
        } else {
            acc.egreso += transaccion.monto;
        }

        return acc;
    }, { ingreso: 0, egreso: 0 });
}

const transacciones: Transaccion[] = [
    { monto: 100000, tipo: "ingreso", categoria: "salario" },
    { monto: 80000, tipo: "egreso", categoria: "comida" },
    { monto: 1200000, tipo: "ingreso", categoria: "Extra" },
    { monto: 50000, tipo: "egreso", categoria: "hobby" }
]

const resultadoTransacciones = agruparTransaccion(transacciones);
console.log(resultadoTransacciones);