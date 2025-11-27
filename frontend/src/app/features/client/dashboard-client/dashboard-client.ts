import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { Header } from '../../../shared/components/header/header';

@Component({
  selector: 'app-dashboard-client',
  standalone: true,
  imports: [CommonModule, RouterOutlet, Header],
  templateUrl: './dashboard-client.html',
  styleUrl: './dashboard-client.scss'
})
export class DashboardClient {

}
