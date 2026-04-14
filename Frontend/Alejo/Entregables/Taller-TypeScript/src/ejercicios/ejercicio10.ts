type PagoTajeta = {
    metodo: "tarjeta";
    numeroTarjeta: string;
    cvv: string;
}

type PagoTransferencia = {
    metodo: "transferencia";
    banco: string;
    numeroCuenta: string;
};

type pagoEfectivo = {
    metodo: "efectivo";
};

type Pago = PagoTajeta | PagoTransferencia | pagoEfectivo;

function validarPago(pago: Pago): boolean {
   
    if (pago.metodo === "tarjeta") {
    return (
      typeof pago.numeroTarjeta === "string" &&
      pago.numeroTarjeta.length >= 12 &&
      typeof pago.cvv === "string" &&
      pago.cvv.length === 3
    );
  }

    else if (pago.metodo === "transferencia") {
        return (
            typeof pago.banco === "string" &&
            pago.banco.length > 0 &&
            typeof pago.numeroCuenta === "string" &&
            pago.numeroCuenta.length > 5
        );
    }
    else {
        //efectivo
        return true;
    }
}

// Pruebas
const pago1: Pago = {
  metodo: "tarjeta",
  numeroTarjeta: "123456789012",
  cvv: "123"
};

const pago2: Pago = {
  metodo: "transferencia",
  banco: "Bancolombia",
  numeroCuenta: "12345678"
};

const pago3: Pago = {
  metodo: "efectivo"
};

const pagoInvalido: Pago = {
  metodo: "tarjeta",
  numeroTarjeta: "123",
  cvv: "12"
};

console.log(validarPago(pago1));
console.log(validarPago(pago2));
console.log(validarPago(pago3));
console.log(validarPago(pagoInvalido)); 