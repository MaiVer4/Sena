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
}


