import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.scss'
})

type EstadoCarga = 'PENDIENTE' | 'CARGANDO' | 'EXITO';


export class App {
  estadoActual: EstadoCarga = 'PENDIENTE';
  items: string[] = [];

  manejarClick() {
    if (this.estadoActual === 'EXITO') {
        this.items = [];
        this.estadoActual = 'PENDIENTE';
    } else if (this.estadoActual === 'PENDIENTE') {
        this.estadoActual = 'CARGANDO';

        setTimeout(() => {
          this.items = ['ONE','TWO','TREE'];
          this.estadoActual = 'EXITO'
        }, 2000);
    }
  }
}


