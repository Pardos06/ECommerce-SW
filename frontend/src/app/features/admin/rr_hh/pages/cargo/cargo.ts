import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MessageService, ConfirmationService } from 'primeng/api';
import { PrimeImportsModule } from '../../../../../prime-imports';

import { Cargo } from '../../interfaces/cargo';
import { CargoService } from '../../services/cargo.service';

@Component({
  selector: 'app-cargo-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './cargo.html',
  styleUrl: './cargo.scss'
})
export class CargoPage implements OnInit {
  cargos: Cargo[] = [];
  cargando = true;

  mostrarFormulario = false;
  editando = false;
  guardando = false;

  form: FormGroup;
  formBusqueda: FormGroup;

  constructor(
    private cargoService: CargoService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {

    this.form = this.fb.group({
      id: [null],
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(5)]]
    });

    this.formBusqueda = this.fb.group({
      nombre: ['']
    });
  }

  ngOnInit(): void {
    this.cargarCargos();
  }

  cargarCargos(): void {
    this.cargando = true;

    this.cargoService.listarCargos().subscribe({
      next: (data) => {
        this.cargos = data;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar los cargos',
          life: 3000
        });
      }
    });
  }

  buscar(): void {
    this.cargarCargos();
  }

  limpiarBusqueda(): void {
    this.formBusqueda.reset();
    this.cargarCargos();
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.form.reset();
    this.mostrarFormulario = true;
  }

  editarCargo(cargo: Cargo): void {
    this.editando = true;
    this.form.patchValue(cargo);
    this.mostrarFormulario = true;
  }

  guardarCargo(): void {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formulario incompleto',
        detail: 'Complete todos los campos correctamente',
        life: 3000
      });
      return;
    }

    this.guardando = true;
    const cargo: Cargo = this.form.value;

    if (this.editando) {
      this.cargoService.editarCargo(cargo).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Actualización exitosa',
            detail: 'El cargo se actualizó correctamente',
            life: 3000
          });
          this.cargarCargos();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al actualizar',
            detail: err.error?.mensaje || 'No se pudo actualizar el cargo',
            life: 4000
          });
        }
      });
    } else {
      this.cargoService.registrarCargo(cargo).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Registro exitoso',
            detail: 'El cargo ha sido creado correctamente',
            life: 3000
          });
          this.cargarCargos();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al registrar',
            detail: err.error?.mensaje || 'No se pudo crear el cargo',
            life: 4000
          });
        }
      });
    }
  }
  eliminarCargo(id: number): void {
  this.cargando = true;

  this.cargoService.eliminarCargo(id).subscribe({
    next: () => {
      this.messageService.add({
        severity: 'success',
        summary: 'Eliminado',
        detail: 'El cargo ha sido eliminado correctamente',
        life: 3000
      });
      this.cargarCargos();
    },
    error: (err) => {
      this.cargando = false;
      this.messageService.add({
        severity: 'error',
        summary: 'Error al eliminar',
        detail: err.error?.mensaje || 'No se pudo eliminar el cargo',
        life: 4000
      });
    }
  });
}

confirmarEliminar(cargo: Cargo): void {
  this.confirmationService.confirm({
    message: `¿Está seguro de eliminar el cargo "${cargo.nombre}"? Esta acción no se puede deshacer.`,
    header: 'Confirmar Eliminación',
    icon: 'pi pi-exclamation-triangle',
    acceptLabel: 'Sí, eliminar',
    rejectLabel: 'Cancelar',
    acceptButtonStyleClass: 'p-button-danger',
    accept: () => {
      this.eliminarCargo(cargo.id!);
    }
  });
}
cancelar(): void {
  this.mostrarFormulario = false;
  this.form.reset();
  this.editando = false;
  }
}
