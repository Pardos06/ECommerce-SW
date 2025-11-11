import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Producto } from '../../interfaces/producto';
import { Categoria } from '../../interfaces/categoria';
import { ProductoService } from '../../services/producto';
import { CategoriaService } from '../../services/categoria';
import { ProductoForm } from '../../interfaces/producto-form';
import { PrimeImportsModule } from '../../../../../prime-imports';
import { MessageService, ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-productos-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './productos.html',
  styleUrls: ['./productos.scss'],
})
export class ProductosPage implements OnInit {
  productos: Producto[] = [];
  categorias: Categoria[] = [];
  form: FormGroup;

  cargando = true;
  editando = false;
  mostrarFormulario = false;
  guardando = false;

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      id: [0],
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      precio: [0, [Validators.required, Validators.min(0.01)]],
      stock: [0, [Validators.required, Validators.min(0)]],
      disponibilidad: ['', Validators.required],
      categoriaId: [0, [Validators.required, Validators.min(1)]],
      imagenNombre: ['']
    });
  }

  ngOnInit(): void {
    this.cargarProductos();
    this.cargarCategorias();
  }

  cargarProductos(): void {
    this.cargando = true;
    this.productoService.listarProductos().subscribe({
      next: (data) => {
        this.productos = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar los productos',
          life: 3000
        });
        this.cargando = false;
      },
    });
  }

  cargarCategorias(): void {
    this.categoriaService.listarCategorias().subscribe({
      next: (data) => (this.categorias = data),
      error: (err) => {
        console.error('Error al cargar categorías:', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar las categorías',
          life: 3000
        });
      },
    });
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.form.reset({
      id: 0,
      nombre: '',
      descripcion: '',
      precio: 0,
      stock: 0,
      disponibilidad: '',
      categoriaId: 0,
      imagenNombre: ''
    });
    this.mostrarFormulario = true;
  }

  guardar(): void {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formulario incompleto',
        detail: 'Por favor complete todos los campos obligatorios correctamente',
        life: 3000
      });
      // Marcar todos los campos como touched para mostrar errores
      Object.keys(this.form.controls).forEach(key => {
        this.form.get(key)?.markAsTouched();
      });
      return;
    }

    this.guardando = true;
    const producto: ProductoForm = this.form.value;
    
    if (this.editando) {
      this.productoService.actualizarProducto(producto).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Actualización exitosa',
            detail: 'El producto ha sido actualizado correctamente',
            life: 3000
          });
          this.cargarProductos();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          console.error('Error al actualizar producto:', err);
          this.messageService.add({
            severity: 'error',
            summary: 'Error al actualizar',
            detail: err.error?.mensaje || 'No se pudo actualizar el producto',
            life: 4000
          });
        },
      });
    } else {
      this.productoService.crearProducto(producto).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Registro exitoso',
            detail: 'El producto ha sido creado correctamente',
            life: 3000
          });
          this.cargarProductos();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          console.error('Error al crear producto:', err);
          this.messageService.add({
            severity: 'error',
            summary: 'Error al crear',
            detail: err.error?.mensaje || 'No se pudo crear el producto',
            life: 4000
          });
        },
      });
    }
  }

  editar(producto: Producto): void {
    const categoria = this.categorias.find((c) => c.nombre === producto.categoria);
    
    this.form.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      stock: producto.stock,
      disponibilidad: producto.disponibilidad,
      categoriaId: categoria?.id || 0,
      imagenNombre: producto.imagenNombre
    });

    this.editando = true;
    this.mostrarFormulario = true;
  }

  confirmarEliminar(producto: Producto): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el producto "${producto.nombre}"? Esta acción no se puede deshacer.`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.eliminarProducto(producto.id);
      }
    });
  }

  eliminarProducto(id: number): void {
    this.productoService.eliminarProducto(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Eliminación exitosa',
          detail: 'El producto ha sido eliminado correctamente',
          life: 3000
        });
        this.cargarProductos();
      },
      error: (err) => {
        console.error('Error al eliminar producto:', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error al eliminar',
          detail: err.error?.mensaje || 'No se pudo eliminar el producto',
          life: 4000
        });
      },
    });
  }

  cancelar(): void {
    this.form.reset({
      id: 0,
      nombre: '',
      descripcion: '',
      precio: 0,
      stock: 0,
      disponibilidad: '',
      categoriaId: 0,
      imagenNombre: ''
    });
    this.editando = false;
    this.mostrarFormulario = false;
  }
}
