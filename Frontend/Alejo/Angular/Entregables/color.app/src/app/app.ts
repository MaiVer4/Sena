import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.scss'
})
export class App {
  colorActual: string = 'white';

  actualizarColor(nuevoColor: string) {
    this.colorActual = nuevoColor;
  }
}
