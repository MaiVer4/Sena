export class AppComponent {
  numeroEnPantalla: string = '0';
  primerValor: number | null = null;
  operacion: string | null = null;
  esperandoSegundoNumero: boolean = false;

  alPresionarTecla(tecla: string) {
    if (this.esNumero(tecla)) {
      this.manejarNumero(tecla);
    } else if (this.esOperador(tecla)) {
      this.manejarOperador(tecla);
    } else if (tecla === '=') {
      this.realizarCalculo();
    }
  }

  private esNumero(valor: string): boolean {
    return !isNaN(Number(valor));
  }

  private esOperador(valor: string): boolean {
    return ['+', '-', '*', '/'].includes(valor);
  }

  private manejarNumero(num: string) {
    if (this.esperandoSegundoNumero) {
      this.numeroEnPantalla = num;
      this.esperandoSegundoNumero = false;
    } else {
      this.numeroEnPantalla = this.numeroEnPantalla === '0' ? num : this.numeroEnPantalla + num;
    }
  }

  private manejarOperador(op: string) {
    this.primerValor = Number(this.numeroEnPantalla);
    this.operacion = op;
    this.esperandoSegundoNumero = true;
  }
}
