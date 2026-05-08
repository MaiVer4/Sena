import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-boton',
  standalone: false,
  templateUrl: './boton.html',
  styleUrl: './boton.scss',
})
export class Boton {
  @Output() colorSeleccionado = new EventEmitter<string>();

  presionar(color: string) {
    this.colorSeleccionado.emit(color)
  }
}
