import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Producto } from '../../interfaces/producto';
import { Categoria } from '../../interfaces/categoria';
import { ProductoService } from '../../services/producto';
import { CategoriaService } from '../../services/categoria';

@Component({
  selector: 'app-productos-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos.html',
  styleUrls: ['./productos.scss']
})
export class ProductosPage implements OnInit {

  productos: Producto[] = [];
  categorias: Categoria[] = [];

  producto: Producto = {
    id: 0,
    nombre: '',
    descripcion: '',
    precio: 0,
    stock: 0,
    disponibilidad: '',
    categoriaId: 0,
    categoria: '',
    imagenNombre: ''
  };

  cargando = true;
  editando = false;

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
      }
    });
  }

  cargarCategorias(): void {
    this.categoriaService.listarCategorias().subscribe({
      next: (data) => (this.categorias = data),
      error: (err) => console.error('Error al cargar categorías:', err)
    });
  }

  guardar(): void {
    if (this.editando) {
      this.productoService.actualizarProducto(this.producto).subscribe({
        next: () => {
          this.cargarProductos();
          this.cancelar();
        },
        error: (err) => console.error('Error al actualizar producto:', err)
      });
    } else {
      this.productoService.crearProducto(this.producto).subscribe({
        next: () => {
          this.cargarProductos();
          this.cancelar();
        },
        error: (err) => console.error('Error al crear producto:', err)
      });
    }
  }

  editar(p: Producto): void {
    this.producto = { ...p };
    this.editando = true;
  }

  eliminar(id: number): void {
    if (confirm('¿Seguro que deseas eliminar este producto?')) {
      this.productoService.eliminarProducto(id).subscribe({
        next: () => this.cargarProductos(),
        error: (err) => console.error('Error al eliminar producto:', err)
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
      categoria: '',
      imagenNombre: ''
    };
    this.editando = false;
  }
}
