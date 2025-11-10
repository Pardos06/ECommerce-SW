import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Producto } from '../../interfaces/producto';
import { Categoria } from '../../interfaces/categoria';
import { ProductoService } from '../../services/producto';
import { CategoriaService } from '../../services/categoria';
import { ProductoForm } from '../../interfaces/producto-form';

import { PrimeImportsModule } from '../../../../../prime-imports';

import { PrimeImportsModule } from '../../../../../prime-imports';

@Component({
  selector: 'app-productos-page',
  standalone: true,
  imports: [CommonModule, FormsModule, PrimeImportsModule],
  templateUrl: './productos.html',
  styleUrls: ['./productos.scss'],
})
export class ProductosPage implements OnInit {
  productos: Producto[] = [];
  productosForm: ProductoForm[] = [];
  categorias: Categoria[] = [];

  producto: ProductoForm = {
    id: 0,
    nombre: '',
    descripcion: '',
    precio: 0,
    stock: 0,
    disponibilidad: '',
    categoriaId: 0,
    imagenNombre: '',
  };

  cargando = true;
  editando = false;
  mostrarFormulario = false;

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService
  ) {}

  ngOnInit(): void {
    this.cargarProductos();
    this.cargarCategorias();
  }

  cargarProductos(): void {
    this.productoService.listarProductos().subscribe({
      next: (data) => {
        this.productos = data; // ahora categoria viene como string
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
        this.cargando = false;
      },
    });
  }

  cargarCategorias(): void {
    this.categoriaService.listarCategorias().subscribe({
      next: (data) => (this.categorias = data),
      error: (err) => console.error('Error al cargar categorías:', err),
    });
  }

  guardar(): void {
    if (this.editando) {
      this.productoService.actualizarProducto(this.producto).subscribe({
        next: () => {
          this.cargarProductos();
          this.cancelar();
        },
        error: (err) => console.error('Error al actualizar producto:', err),
      });
    } else {
      this.productoService.crearProducto(this.producto).subscribe({
        next: () => {
          this.cargarProductos();
          this.cancelar();
        },
        error: (err) => console.error('Error al crear producto:', err),
      });
    }
  }

  editar(producto: Producto): void {
    const categoria = this.categorias.find((c) => c.nombre === producto.categoria);
    const productoEditar: ProductoForm = {
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      stock: producto.stock,
      disponibilidad: producto.disponibilidad,
      categoriaId: categoria?.id || 0,
      imagenNombre: producto.imagenNombre,
    };

    this.producto = productoEditar;
    this.editando = true;
    this.mostrarFormulario = true;
  }

  eliminar(id: number): void {
    if (confirm('¿Seguro que deseas eliminar este producto?')) {
      this.productoService.eliminarProducto(id).subscribe({
        next: () => this.cargarProductos(),
        error: (err) => console.error('Error al eliminar producto:', err),
      });
    }
  }

  cancelar(): void {
    this.producto = {
      id: 0,
      nombre: '',
      descripcion: '',
      precio: 0,
      stock: 0,
      disponibilidad: '',
      categoriaId: 0,
      imagenNombre: '',
    };
    this.editando = false;
    this.mostrarFormulario = false;
  }
}
