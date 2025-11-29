import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Compra } from '../../interfaces/compra';
import { CompraForm } from '../../interfaces/compra.form';
import { CompraService } from '../../services/compra.service';
import { MetodoPagoService } from '../../services/metodo-pago.service';
import { ProveedorService } from '../../../proveedores/services/proveedor.service';
import { EmpleadoService } from '../../../usuarios/services/empleado.service';
import { PrimeImportsModule } from '../../../../../prime-imports';
import { MessageService, ConfirmationService } from 'primeng/api';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-compra',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './compra.html',
  styleUrl: './compra.scss'
})
export class CompraPage implements OnInit {

  compras: Compra[] = [];
  metodosPago: { id: number; nombre: string }[] = [];
  proveedores: { id: number; nombre: string }[] = [];
  empleados: { id: number; nombreUsuario: string }[] = [];
  form: FormGroup;
  cargando = true;
  editando = false;
  mostrarFormulario = false;
  guardando = false;

  estadosCompra = [
    { label: 'Pendiente', value: 'Pendiente' },
    { label: 'Pagada', value: 'Pagada' },
    { label: 'Cancelada', value: 'Cancelada' }
  ];

  // Fecha máxima permitida (hoy en formato datetime-local)
  fechaMaxima: string;

  constructor(
    private readonly compraService: CompraService,
    private readonly metodoPagoService: MetodoPagoService,
    private readonly proveedorService: ProveedorService,
    private readonly empleadoService: EmpleadoService,
    private readonly fb: FormBuilder,
    private readonly messageService: MessageService,
    private readonly confirmationService: ConfirmationService
  ) {
    // Establecer fecha máxima como ahora (formato: yyyy-MM-ddTHH:mm)
    const now = new Date();
    this.fechaMaxima = new Date(now.getTime() - now.getTimezoneOffset() * 60000)
      .toISOString()
      .slice(0, 16);

    this.form = this.fb.group({
      id: [0],
      fechaCompra: ['', Validators.required],
      estado: ['Pendiente', Validators.required],
      metodoPagoId: [0, [Validators.required, Validators.min(1)]],
      proveedorId: [0, [Validators.required, Validators.min(1)]],
      empleadoId: [0, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.cargarCompras();
    this.cargarMetodosPago();
    this.cargarProveedores();
    this.cargarEmpleados();
  }

  cargarCompras(): void {
    this.cargando = true;
    this.compraService.listarCompras().subscribe({
      next: data => {
        this.compras = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.cargando = false;
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar las compras', 
          life: 3000 
        });
      }
    });
  }

  cargarMetodosPago(): void {
    this.metodoPagoService.listar().subscribe({
      next: data => {
        this.metodosPago = data
          .filter((m): m is { id: number; nombre: string; descripcion: string } => m.id !== null)
          .map(m => ({ id: m.id, nombre: m.nombre }));
      },
      error: err => {
        console.error('No se pudieron cargar los métodos de pago', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los métodos de pago', 
          life: 3000 
        });
      }
    });
  }

  cargarProveedores(): void {
    this.proveedorService.listarProveedores().subscribe({
      next: data => {
        this.proveedores = data.map(p => ({ 
          id: p.id, 
          nombre: p.nombre 
        }));
      },
      error: err => {
        console.error('No se pudieron cargar los proveedores', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los proveedores', 
          life: 3000 
        });
      }
    });
  }

  cargarEmpleados(): void {
    this.empleadoService.listarEmpleados().subscribe({
      next: data => {
        this.empleados = data.map(e => ({ 
          id: e.id, 
          nombreUsuario: e.nombreUsuario 
        }));
      },
      error: err => {
        console.error('No se pudieron cargar los empleados', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los empleados', 
          life: 3000 
        });
      }
    });
  }

  mostrarDialogo(): void {
    this.editando = false;
    const fechaActual = new Date().toISOString().slice(0, 16);
    this.form.reset({ 
      id: 0, 
      fechaCompra: fechaActual, 
      estado: 'Pendiente', 
      metodoPagoId: 0, 
      proveedorId: 0, 
      empleadoId: 0 
    });
    this.mostrarFormulario = true;
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
      const compraForm: CompraForm = {
        id: this.editando ? this.form.value.id : undefined,
        fechaCompra: this.form.value.fechaCompra,
        estado: this.form.value.estado,
        metodoPagoId: Number(this.form.value.metodoPagoId),
        proveedorId: Number(this.form.value.proveedorId),
        empleadoId: Number(this.form.value.empleadoId)
      };

      if (this.editando) {
        await firstValueFrom(this.compraService.actualizarCompra(compraForm));
      } else {
        await firstValueFrom(this.compraService.crearCompra(compraForm));
      }

      this.guardando = false;
      this.messageService.add({
        severity: 'success',
        summary: this.editando ? 'Actualización exitosa' : 'Registro exitoso',
        detail: `La compra ha sido ${this.editando ? 'actualizada' : 'registrada'} correctamente`,
        life: 3000
      });

      this.cargarCompras();
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
          detail: err.error?.mensaje || err.error?.message || 'Ocurrió un error al guardar la compra',
          life: 5000
        });
      }
    }
  }

  editar(compra: Compra): void {
    // Convertir fecha ISO a formato datetime-local
    const fecha = new Date(compra.fechaCompra).toISOString().slice(0, 16);
    
    this.form.patchValue({
      id: compra.id,
      fechaCompra: fecha,
      estado: compra.estado,
      metodoPagoId: 0, // No tenemos esta info en la respuesta, debemos ajustar
      proveedorId: this.buscarIdPorNombre(this.proveedores, compra.proveedor),
      empleadoId: this.buscarIdPorNombreEmpleado(compra.empleado)
    });
    this.editando = true;
    this.mostrarFormulario = true;
  }

  buscarIdPorNombre(lista: { id: number; nombre: string }[], nombre: string): number {
    const item = lista.find(i => i.nombre === nombre);
    return item ? item.id : 0;
  }

  buscarIdPorNombreEmpleado(nombreEmpleado: string): number {
    const empleado = this.empleados.find(e => e.nombreUsuario === nombreEmpleado);
    return empleado ? empleado.id : 0;
  }

  confirmarEliminar(compra: Compra): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar la compra #${compra.id}? Esta acción no se puede deshacer.`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.eliminarCompra(compra.id)
    });
  }

  eliminarCompra(id: number): void {
    this.compraService.eliminarCompra(id).subscribe({
      next: () => {
        this.messageService.add({ 
          severity: 'success', 
          summary: 'Eliminación exitosa', 
          detail: 'La compra ha sido eliminada correctamente', 
          life: 3000 
        });
        this.cargarCompras();
      },
      error: err => {
        console.error(err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: err.error?.mensaje || 'No se pudo eliminar la compra. Puede tener detalles asociados.', 
          life: 3000 
        });
      }
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    const fechaActual = new Date().toISOString().slice(0, 16);
    this.form.reset({ 
      id: 0, 
      fechaCompra: fechaActual, 
      estado: 'Pendiente', 
      metodoPagoId: 0, 
      proveedorId: 0, 
      empleadoId: 0 
    });
    this.editando = false;
  }

  formatearFecha(fecha: string): string {
    return new Date(fecha).toLocaleString('es-ES', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  getBadgeClass(estado: string): string {
    switch(estado) {
      case 'Pendiente': return 'bg-warning';
      case 'Pagada': return 'bg-success';
      case 'Cancelada': return 'bg-danger';
      default: return 'bg-secondary';
    }
  }
}
