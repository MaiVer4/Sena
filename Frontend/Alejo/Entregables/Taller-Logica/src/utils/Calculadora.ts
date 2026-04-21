export class Calculadora {

    sumar(a: number, b: number) {
        return a + b;
    }

    restar(a: number, b: number) {
        return a - b;
    }

    multiplicar(a: number, b: number) {
        return a * b;
    }

    dividir(a: number, b: number) {
        if (b == 0) {
            throw new Error("No se puede dividir por 0");
        }
        return a / b;
    }

    calcular(operacion: string, a: number, b: number) {
        switch (operacion) {
            case "sumar":
                return this.sumar(a, b);

            case "restar":
                return this.restar(a, b);

            case "multiplicar":
                return this.multiplicar(a, b);

            case "dividir":
                return this.dividir(a, b);

            default:
                throw new Error(`La operación '${operacion}' no es válida`);
        }
    }
}

