import { Component, NgZone, ChangeDetectorRef } from '@angular/core';

type EstadoCarga = 'PENDIENTE' | 'CARGANDO' | 'EXITO';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false, 
  styleUrl: './app.scss'
})
export class App {
  estadoActual: EstadoCarga = 'PENDIENTE';
  items: string[] = [];

  constructor(private ngZone: NgZone, private cdr: ChangeDetectorRef) {}

  manejarClick() {
    console.log('Click detectado, estado actual:', this.estadoActual);
    if (this.estadoActual === 'EXITO') {
      this.items = [];
      this.estadoActual = 'PENDIENTE';
    } else if (this.estadoActual === 'PENDIENTE') {
      this.estadoActual = 'CARGANDO';
      this.cdr.markForCheck();
      console.log('Entré en setTimeout, estado: CARGANDO');

      this.ngZone.runOutsideAngular(() => {
        setTimeout(() => {
          console.log('Dentro del setTimeout, cambiando a EXITO');
          this.ngZone.run(() => {
            this.items = ['ONE', 'TWO', 'TREE'];
            this.estadoActual = 'EXITO';
            this.cdr.markForCheck();
            console.log('Estado después del cambio:', this.estadoActual, 'Items:', this.items);
          });
        }, 2000);
      });
    }
  }
}