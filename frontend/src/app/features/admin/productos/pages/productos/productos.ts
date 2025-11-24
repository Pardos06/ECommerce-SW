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
  imagenPreview: string | null = null;


  constructor(
    public productoService: ProductoService,
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
    setTimeout(() => {
    if (this.productos.length > 0) {
      const primeraImagen = this.productos[0].imagenNombre;
      console.log('Primera imagen nombre:', primeraImagen);
      console.log('URL generada:', this.productoService.obtenerUrlImagen(primeraImagen));
    }
  }, 2000);
  }

  onFileSelected(event: any): void {
  if (event.target.files && event.target.files.length > 0) {
    const file = event.target.files[0] as File;
    
    // Validación de tipo de archivo
    const tiposPermitidos = ['image/jpeg', 'image/jpg', 'image/png', 'image/webp', 'image/gif'];
    if (!tiposPermitidos.includes(file.type)) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formato no válido',
        detail: 'Solo se permiten imágenes (JPG, PNG, WEBP, GIF)',
        life: 3000
      });
      return;
    }
    
    // Validación de tamaño (máximo 5MB)
    const maxSize = 5 * 1024 * 1024; // 5MB en bytes
    if (file.size > maxSize) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Archivo muy grande',
        detail: 'La imagen no debe superar los 5MB',
        life: 3000
      });
      return;
    }

    this.imagenArchivo = file;
    this.imagenPreview = URL.createObjectURL(file);
    console.log('🖼️ Imagen seleccionada:', file.name, `(${(file.size / 1024).toFixed(2)} KB)`);
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
    this.messageService.add({ 
      severity: 'warn', 
      summary: 'Formulario incompleto', 
      detail: 'Complete los campos obligatorios', 
      life: 3000 
    });
    return;
  }

  this.guardando = true;
  const producto: ProductoForm = this.form.value;

  try {
    // ⭐ Si hay una imagen nueva seleccionada, súbela primero
    if (this.imagenArchivo) {
      console.log('📤 Subiendo imagen:', this.imagenArchivo.name);
      const imagenResp = await firstValueFrom(this.productoService.subirImagen(this.imagenArchivo));
      
      if (imagenResp && imagenResp.nombreArchivo) {
        producto.imagenNombre = imagenResp.nombreArchivo;
        console.log('✅ Imagen subida:', imagenResp.nombreArchivo);
      }
    } else if (this.editando && !this.imagenArchivo) {
      // Si estamos editando y NO hay nueva imagen, mantén la existente
      console.log('📌 Manteniendo imagen existente:', producto.imagenNombre);
    }

    // Crear o actualizar el producto
    const observable = this.editando
      ? this.productoService.actualizarProducto(producto)
      : this.productoService.crearProducto(producto);

    await firstValueFrom(observable);
    
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
    console.error('❌ Error al guardar producto:', err);
    this.messageService.add({
      severity: 'error',
      summary: 'Error',
      detail: err.error?.message || err.message || 'Ocurrió un error al guardar el producto',
      life: 5000
    });
  }
}

  editar(producto: Producto): void {
    const categoria = this.categorias.find(c => c.nombre === producto.categoria);

    this.form.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      stock: producto.stock,
      disponibilidad: producto.disponibilidad,
      categoriaId: categoria?.id || 0,
      imagenNombre: producto.imagenNombre || '' // ⭐ Mantiene el nombre de la imagen existente
    });

    this.editando = true;
    this.mostrarFormulario = true;

    // Mostrar preview de la imagen existente
    if (producto.imagenNombre) {
      this.imagenPreview = this.productoService.obtenerUrlImagen(producto.imagenNombre);
      console.log('🖼️ Preview de imagen existente:', this.imagenPreview);
    } else {
      this.imagenPreview = null;
    }

    this.imagenArchivo = undefined; // Limpia el archivo seleccionado
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
    
    // Limpia la imagen y preview
    this.imagenArchivo = undefined;
    this.imagenPreview = null;
    
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
