import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.scss'
})
export class App {
  numeroEnPantalla: string = '0';

  alPresionarTecla(tecla: string) {
    if (this.numeroEnPantalla === '0') {
      this.numeroEnPantalla = tecla;
    } else {
      this.numeroEnPantalla += tecla;
    }
  }

}
