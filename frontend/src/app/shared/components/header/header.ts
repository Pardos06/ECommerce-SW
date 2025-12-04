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
  rol: string | null = null;

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
      // usar el método del service o localStorage (según implementación actual)
      this.rol = this.authService.obtenerRol() || localStorage.getItem('rol');
    } else {
      this.nombreUsuario = null;
      this.rol = null;
    }
  }

  navegarHome(): void {
    this.router.navigate(['/home']);
  }

  navegarLogin(): void {
    this.router.navigate(['/login']);
  }

  navegarProductos(): void {
    // si tu ruta base es '/cliente' la navegamos así
    this.router.navigate(['/cliente/productos']);
  }

  navegarCarrito(): void {
    this.router.navigate(['/cliente/carrito']);
  }

  navegarCheckout(): void {
    this.router.navigate(['/cliente/checkout']);
  }

  navegarOrdenes(): void {
    // para clientes: listado de sus órdenes; para admin, listado general
    this.router.navigate(['/cliente/mis-ordenes']);
  }

  cerrarSesion(): void {
    this.authService.cerrarSesion();
    this.isAuthenticated = false;
    this.nombreUsuario = null;
    this.rol = null;
    this.router.navigate(['/login']);
  }

  // helpers para template (más legibles que repetir comparaciones)
  esCliente(): boolean {
    if (!this.rol) return false;
    return this.rol.toLowerCase() === 'cliente' || this.rol.toLowerCase() === 'cliente';
  }

  esAdmin(): boolean {
    if (!this.rol) return false;
    return this.rol.toLowerCase() === 'administrador' || this.rol.toLowerCase() === 'admin';
  }
}
