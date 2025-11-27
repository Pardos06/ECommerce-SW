import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProductoService } from '../../../features/admin/productos/services/producto.service';
import { Producto } from '../../../features/admin/productos/interfaces/producto';
import { Header } from '../../../shared/components/header/header';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, Header],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class HomePage implements OnInit {

  productos: Producto[] = [];
  cargando = true;

  constructor(
    public productoService: ProductoService, // ⭐ Cambiar de private a public
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando = true;
    this.productoService.listarProductos().subscribe({
      next: (data) => {
        this.productos = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar productos', err);
        this.cargando = false;
      }
    });
  }

  irAlLogin(): void {
    this.router.navigate(['/login']);
  }

  // ⭐ Método auxiliar para manejar errores de imagen
  onImageError(event: any): void {
    event.target.src = 'assets/images/no-image.png'; // Imagen por defecto
    event.target.style.opacity = '0.5';
  }
}