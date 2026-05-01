import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-keyboard',
  standalone: false,
  templateUrl: './keyboard.html',
  styleUrl: './keyboard.scss',
})
export class Keyboard {
  @Output() pulsarBoton = new EventEmitter<string>();

  clickEnTecla(tecla: string) {
    this.pulsarBoton.emit(tecla)
  }
}
