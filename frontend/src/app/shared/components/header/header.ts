import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { Auth } from '../../../core/services/auth';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule, ButtonModule],
  templateUrl: './header.html',
  styleUrl: './header.scss'
})
export class Header implements OnInit {
  isAuthenticated = false;
  nombreUsuario: string | null = null;

  constructor(
    private authService: Auth,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.verificarAutenticacion();
  }

  verificarAutenticacion(): void {
    const token = this.authService.obtenerToken();
    this.isAuthenticated = !!token;
    
    if (this.isAuthenticated) {
      this.nombreUsuario = localStorage.getItem('nombre');
    }
  }

  navegarHome(): void {
    this.router.navigate(['/home']);
  }

  navegarLogin(): void {
    this.router.navigate(['/login']);
  }

  cerrarSesion(): void {
    this.authService.cerrarSesion();
    this.isAuthenticated = false;
    this.nombreUsuario = null;
    this.router.navigate(['/login']);
  }
}
