import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CompraDetails } from '../../interfaces/compra.details';
import { CompraDetailForm } from '../../interfaces/compra.detail.form';
import { CompraDetailsService } from '../../services/compra-details.service';
import { CompraService } from '../../services/compra.service';
import { ProductoService } from '../../../productos/services/producto.service';
import { PrimeImportsModule } from '../../../../../prime-imports';
import { MessageService, ConfirmationService } from 'primeng/api';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-compra-details',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './compra-details.html',
  styleUrl: './compra-details.scss'
})
export class CompraDetailsPage implements OnInit {

  detalles: CompraDetails[] = [];
  compras: { id: number; info: string }[] = [];
  productos: { id: number; nombre: string; precio: number }[] = [];
  form: FormGroup;
  cargando = true;
  editando = false;
  mostrarFormulario = false;
  guardando = false;

  constructor(
    private readonly compraDetailsService: CompraDetailsService,
    private readonly compraService: CompraService,
    private readonly productoService: ProductoService,
    private readonly fb: FormBuilder,
    private readonly messageService: MessageService,
    private readonly confirmationService: ConfirmationService
  ) {
    this.form = this.fb.group({
      id: [0],
      compraId: [0, [Validators.required, Validators.min(1)]],
      productoId: [0, [Validators.required, Validators.min(1)]],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      precioUnitario: [0, [Validators.required, Validators.min(0.01)]]
    });
  }

  ngOnInit(): void {
    this.cargarDetalles();
    this.cargarCompras();
    this.cargarProductos();
  }

  cargarDetalles(): void {
    this.cargando = true;
    this.compraDetailsService.listarDetalles().subscribe({
      next: data => {
        this.detalles = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.cargando = false;
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los detalles de compra', 
          life: 3000 
        });
      }
    });
  }

  cargarCompras(): void {
    this.compraService.listarCompras().subscribe({
      next: data => {
        this.compras = data.map(c => ({ 
          id: c.id, 
          info: `Compra #${c.id} - ${c.proveedor} (${c.estado})`
        }));
      },
      error: err => {
        console.error('No se pudieron cargar las compras', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar las compras', 
          life: 3000 
        });
      }
    });
  }

  cargarProductos(): void {
    this.productoService.listarProductos().subscribe({
      next: data => {
        this.productos = data.map(p => ({ 
          id: p.id, 
          nombre: p.nombre,
          precio: p.precio
        }));
      },
      error: err => {
        console.error('No se pudieron cargar los productos', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los productos', 
          life: 3000 
        });
      }
    });
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.form.reset({ 
      id: 0, 
      compraId: 0, 
      productoId: 0, 
      cantidad: 1, 
      precioUnitario: 0 
    });
    this.mostrarFormulario = true;
  }

  onProductoChange(): void {
    const productoId = this.form.get('productoId')?.value;
    if (productoId && productoId > 0) {
      const producto = this.productos.find(p => p.id === productoId);
      if (producto) {
        this.form.patchValue({ precioUnitario: producto.precio });
      }
    }
  }

  calcularSubtotal(): number {
    const cantidad = this.form.get('cantidad')?.value || 0;
    const precio = this.form.get('precioUnitario')?.value || 0;
    return cantidad * precio;
  }

  async guardar(): Promise<void> {
    if (this.form.invalid) {
      for (const key of Object.keys(this.form.controls)) {
        this.form.get(key)?.markAsTouched();
      }
      this.messageService.add({ 
        severity: 'warn', 
        summary: 'Formulario incompleto', 
        detail: 'Complete los campos obligatorios', 
        life: 3000 
      });
      return;
    }

    this.guardando = true;

    try {
      const detailForm: CompraDetailForm = {
        id: this.editando ? this.form.value.id : 0,
        compraId: Number(this.form.value.compraId),
        productoId: Number(this.form.value.productoId),
        cantidad: Number(this.form.value.cantidad),
        precioUnitario: Number(this.form.value.precioUnitario)
      };

      if (this.editando) {
        await firstValueFrom(this.compraDetailsService.actualizarDetalle(detailForm));
      } else {
        await firstValueFrom(this.compraDetailsService.crearDetalle(detailForm));
      }

      this.guardando = false;
      this.messageService.add({
        severity: 'success',
        summary: this.editando ? 'Actualización exitosa' : 'Registro exitoso',
        detail: `El detalle ha sido ${this.editando ? 'actualizado' : 'registrado'} correctamente`,
        life: 3000
      });

      this.cargarDetalles();
      this.cancelar();

    } catch (err: any) {
      this.guardando = false;
      console.error(err);

      if (err.status === 403) {
        this.messageService.add({
          severity: 'error',
          summary: 'Acción no permitida',
          detail: 'No tiene permisos para realizar esta acción',
          life: 5000
        });
      } else {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: err.error?.mensaje || err.error?.message || 'Ocurrió un error al guardar el detalle',
          life: 5000
        });
      }
    }
  }

  editar(detalle: CompraDetails): void {
    this.form.patchValue({
      id: detalle.id,
      compraId: detalle.compraId,
      productoId: detalle.productoId,
      cantidad: detalle.cantidad,
      precioUnitario: detalle.precioUnitario
    });
    this.editando = true;
    this.mostrarFormulario = true;
  }

  confirmarEliminar(detalle: CompraDetails): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el detalle del producto "${detalle.producto}"?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.eliminarDetalle(detalle.id)
    });
  }

  eliminarDetalle(id: number): void {
    this.compraDetailsService.eliminarDetalle(id).subscribe({
      next: () => {
        this.messageService.add({ 
          severity: 'success', 
          summary: 'Eliminación exitosa', 
          detail: 'El detalle ha sido eliminado correctamente', 
          life: 3000 
        });
        this.cargarDetalles();
      },
      error: err => {
        console.error(err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: err.error?.mensaje || 'No se pudo eliminar el detalle', 
          life: 3000 
        });
      }
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.form.reset({ 
      id: 0, 
      compraId: 0, 
      productoId: 0, 
      cantidad: 1, 
      precioUnitario: 0 
    });
    this.editando = false;
  }

  calcularSubtotalFila(detalle: CompraDetails): number {
    return detalle.cantidad * detalle.precioUnitario;
  }
}
