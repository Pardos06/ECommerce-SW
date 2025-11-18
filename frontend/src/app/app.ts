import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PrimeImportsModule } from './prime-imports';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,PrimeImportsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
}
