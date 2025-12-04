import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { ProductoClientService } from '../../../productos/services/producto-client.service';
import { Producto } from '../../../productos/interfaces/producto';
import { CartService } from '../../../carrito/services/cart.service';
import { Header } from '../../../../../shared/components/header/header';
import { Footer } from '../../../../../shared/components/footer/footer';

@Component({
  selector: 'app-productos-client',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, Header, Footer],
  templateUrl: './productos.html',
  styleUrls: ['./productos.scss']
})
export class ProductosClientPage implements OnInit {

  productos: Producto[] = [];
  cargando = true;
  cantidades: Record<number, number> = {};

  constructor(
    public productoService: ProductoClientService,
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.cargando = true;
    this.productoService.listarProductos().subscribe({
      next: (data) => {
        this.productos = data || [];
        this.productos.forEach(p => {
          const id = p.id;
          this.cantidades[id] = 1;
        });
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar productos', err);
        this.cargando = false;
      }
    });
  }

  agregar(producto: Producto) {
    if (!producto) return;
    const id = producto.id;
    const cantidadSeleccionada = this.cantidades[id] ?? 1;

    if (cantidadSeleccionada < 1) {
      console.warn('Cantidad mínima 1');
      this.cantidades[id] = 1;
      return;
    }
    if (cantidadSeleccionada > producto.stock) {
      console.warn('Cantidad mayor al stock disponible');
      this.cantidades[id] = producto.stock;
      return;
    }

    this.cartService.addItem(producto, cantidadSeleccionada);
  }

  verDetalle(id: number): void {
    this.router.navigate(['/cliente/productos', id]);
  }

  onImageError(event: any): void {
    event.target.src = 'assets/images/no-image.png';
    event.target.style.opacity = '0.5';
  }
}
