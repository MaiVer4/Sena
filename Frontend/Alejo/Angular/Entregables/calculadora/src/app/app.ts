import { Component } from "@angular/core";

@Component({
  selector: 'app-root',
  standalone: false,
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class AppComponent {
  numeroEnPantalla: string = '0';
  primerValor: number | null = null;
  operacion: string | null = null;
  esperandoSegundoNumero: boolean = false;

  alPresionarTecla(tecla: string) {
    if (tecla === 'C') {
      this.limpiarCalculadora();
    } else if (this.esNumero(tecla)) {
      this.manejarNumero(tecla);
    } else if (this.esOperador(tecla)) {
      this.manejarOperador(tecla);
    } else if (tecla === '=') {
      this.realizarCalculo();
    }
  }

  private limpiarCalculadora() {
    this.numeroEnPantalla = '0';
    this.primerValor = null;
    this.operacion = null;
    this.esperandoSegundoNumero = false;
  }
  private esNumero(valor: string): boolean {
    return !isNaN(Number(valor));
  }

  private esOperador(valor: string): boolean {
    return ['+', '-', '*', '/'].includes(valor);
  }

  private manejarNumero(num: string) {
    if (this.numeroEnPantalla.length >= 15) return;

    if (this.numeroEnPantalla === '0') {
      this.numeroEnPantalla = num;
    } else {
      this.numeroEnPantalla += num;
    }
  }

  private manejarOperador(op: string) {
    this.primerValor = Number(this.numeroEnPantalla);
    this.operacion = op;
    this.esperandoSegundoNumero = true;

    this.numeroEnPantalla = `${this.primerValor} ${op}`;
  }

  private realizarCalculo() {
    if (this.primerValor === null || this.operacion === null) return;

    const partes = this.numeroEnPantalla.split(` ${this.operacion}`)
    const segundoValor = Number(this.numeroEnPantalla);

    if (isNaN(segundoValor)) return;
      
    let resultado: number = 0;

    switch (this.operacion) {
      case '+':
        resultado = this.primerValor + segundoValor;
        break;
      case '-':
        resultado = this.primerValor - segundoValor;
        break;
      case '*':
        resultado = this.primerValor * segundoValor;
        break;
      case '/':
        resultado = segundoValor !== 0 ? this.primerValor / segundoValor : 0; // Evitamos dividir por cero
        break;
    }

    this.numeroEnPantalla = resultado.toString();
    this.primerValor = null;
    this.operacion = null;
  }

}