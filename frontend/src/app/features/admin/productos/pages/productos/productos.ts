import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Producto } from '../../interfaces/producto';
import { Categoria } from '../../interfaces/categoria';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { ProductoForm } from '../../interfaces/producto-form';
import { PrimeImportsModule } from '../../../../../prime-imports';
import { MessageService, ConfirmationService } from 'primeng/api';
import { firstValueFrom } from 'rxjs';

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
  imagenArchivo?: File;
  cargando = true;
  editando = false;
  mostrarFormulario = false;
  guardando = false;
  busqueda = '';

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
  get imagenPreview(): string | null {
    return this.imagenArchivo ? URL.createObjectURL(this.imagenArchivo) : null;
  }
  ngOnInit(): void {
    this.cargarProductos();
    this.cargarCategorias();
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      this.imagenArchivo = event.target.files[0];
    }
  }

  cargarProductos(): void {
    this.cargando = true;
    this.productoService.listarProductos().subscribe({
      next: (data) => { this.productos = data; this.cargando = false; },
      error: (err: any) => {
        this.cargando = false;
        console.error(err);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudieron cargar los productos', life: 3000 });
      }
    });
  }

  cargarCategorias(): void {
    this.categoriaService.listarCategorias().subscribe({
      next: (data) => (this.categorias = data),
      error: (err: any) => {
        console.error(err);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudieron cargar las categorías', life: 3000 });
      }
    });
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.imagenArchivo = undefined;
    this.form.reset({ id: 0, nombre: '', descripcion: '', precio: 0, stock: 0, disponibilidad: '', categoriaId: 0, imagenNombre: '' });
    this.mostrarFormulario = true;
  }

  async guardar(): Promise<void> {
    if (this.form.invalid) {
      Object.keys(this.form.controls).forEach(key => this.form.get(key)?.markAsTouched());
      this.messageService.add({ severity: 'warn', summary: 'Formulario incompleto', detail: 'Complete los campos obligatorios', life: 3000 });
      return;
    }

    this.guardando = true;
    const producto: ProductoForm = this.form.value;

    try {
      // Si hay imagen, la subimos primero
      if (this.imagenArchivo) {
        const imagenResp = await firstValueFrom(this.productoService.subirImagen(this.imagenArchivo));
        if (imagenResp) {
          producto.imagenNombre = imagenResp.nombreArchivo;
        }
      }

      // Crear o actualizar
      const observable = this.editando
        ? this.productoService.actualizarProducto(producto)
        : this.productoService.crearProducto(producto);

      const res = await firstValueFrom(observable);
      this.guardando = false;
      this.messageService.add({
        severity: 'success',
        summary: this.editando ? 'Actualización exitosa' : 'Registro exitoso',
        detail: `El producto ha sido ${this.editando ? 'actualizado' : 'creado'} correctamente`,
        life: 3000
      });
      this.cargarProductos();
      this.cancelar();
    } catch (err: any) {
      this.guardando = false;
      console.error('Error al guardar producto:', err);
      this.messageService.add({
        severity: 'error',
        summary: 'Error',
        detail: err.error?.mensaje || 'Ocurrió un error al guardar el producto',
        life: 5000
      });
    }
  }

  editar(producto: Producto): void {
    const categoria = this.categorias.find(c => c.nombre === producto.categoria);
    this.form.patchValue({ ...producto, categoriaId: categoria?.id || 0 });
    this.editando = true;
    this.mostrarFormulario = true;
  }

  confirmarEliminar(producto: Producto): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el producto "${producto.nombre}"?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.eliminarProducto(producto.id)
    });
  }

  eliminarProducto(id: number): void {
    this.productoService.eliminarProducto(id).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Eliminación exitosa', detail: 'Producto eliminado correctamente', life: 3000 });
        this.cargarProductos();
      },
      error: (err: any) => {
        console.error(err);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar el producto', life: 4000 });
      }
    });
  }

  cancelar(): void {
    this.form.reset({ id: 0, nombre: '', descripcion: '', precio: 0, stock: 0, disponibilidad: '', categoriaId: 0, imagenNombre: '' });
    this.imagenArchivo = undefined;
    this.editando = false;
    this.mostrarFormulario = false;
  }

  buscar(): void {
    if (!this.busqueda) {
      this.cargarProductos();
      return;
    }
    this.productoService.buscarProductosDisponibles(this.busqueda).subscribe({
      next: data => this.productos = data,
      error: (err: any) => console.error(err)
    });
  }
}
