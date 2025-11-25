import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MetodoPago } from '../../interfaces/metodo.pago';
import { MetodoPagoService } from '../../services/metodo-pago.service';
import { CommonModule } from '@angular/common';
import { MessageService, ConfirmationService } from 'primeng/api';
import { PrimeImportsModule } from '../../../../../prime-imports';

@Component({
  selector: 'app-metodo-pago-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './metodo-pago.html',
  styleUrls: ['./metodo-pago.scss']
})
export class MetodoPagoPage implements OnInit {
  metodos: MetodoPago[] = [];
  form: FormGroup;
  cargando: boolean = true;
  mostrarFormulario: boolean = false;
  editando: boolean = false;
  guardando: boolean = false;

  constructor(
    private metodoPagoService: MetodoPagoService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.form = this.fb.group({
      id: [null],
      nombre: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  ngOnInit(): void {
    this.cargarMetodos();
  }

  cargarMetodos(): void {
    this.cargando = true;
    this.metodoPagoService.listar().subscribe({
      next: (data) => {
        this.metodos = data;
        this.cargando = false;
      },
      error: (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar los métodos de pago',
          life: 3000
        });
        this.cargando = false;
      }
    });
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.form.reset();
    this.mostrarFormulario = true;
  }

  editarMetodo(metodo: MetodoPago): void {
    this.editando = true;
    this.form.patchValue(metodo);
    this.mostrarFormulario = true;
  }

  guardarMetodo(): void {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formulario incompleto',
        detail: 'Por favor complete todos los campos correctamente',
        life: 3000
      });
      return;
    }

    this.guardando = true;
    const metodo: MetodoPago = this.form.value;

    const request = this.editando
      ? this.metodoPagoService.editar(metodo)
      : this.metodoPagoService.registrar(metodo);

    request.subscribe({
      next: () => {
        this.guardando = false;
        this.messageService.add({
          severity: 'success',
          summary: this.editando ? 'Actualización exitosa' : 'Registro exitoso',
          detail: this.editando
            ? 'El método de pago ha sido actualizado correctamente'
            : 'El método de pago ha sido registrado correctamente',
          life: 3000
        });
        this.cargarMetodos();
        this.cancelar();
      },
      error: (err) => {
        this.guardando = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: err.error?.mensaje || 'No se pudo completar la operación',
          life: 4000
        });
      }
    });
  }

  confirmarEliminar(metodo: MetodoPago): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el método "${metodo.nombre}"? Esta acción no se puede deshacer.`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.eliminarMetodo(metodo.id!)
    });
  }

  eliminarMetodo(id: number): void {
    this.metodoPagoService.eliminar(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Eliminación exitosa',
          detail: 'El método de pago ha sido eliminado correctamente',
          life: 3000
        });
        this.cargarMetodos();
      },
      error: (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error al eliminar',
          detail: err.error?.mensaje || 'No se pudo eliminar el método de pago',
          life: 4000
        });
      }
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.form.reset();
    this.editando = false;
  }
}
