import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-display',
  standalone: false,
  templateUrl: './display.html',
  styleUrl: './display.scss',
})
export class Display {
  @Input() valor: string = '0';
}
