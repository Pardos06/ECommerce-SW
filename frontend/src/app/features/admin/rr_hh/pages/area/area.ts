import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MessageService, ConfirmationService } from 'primeng/api';
import { PrimeImportsModule } from '../../../../../prime-imports';

import { Area } from '../../interfaces/area';
import { AreaService } from '../../services/area.service';

@Component({
  selector: 'app-area-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './area.html',
  styleUrl: './area.scss'
})

export class AreaPage implements OnInit {
  areas: Area[] = [];
  cargando: boolean = true;
  
  mostrarFormulario: boolean = false;
  editando: boolean = false;
  guardando: boolean = false;
  form: FormGroup;

  constructor(
    private areaService: AreaService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.form = this.fb.group({
      id: [null],
      nombre: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      descripcion: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(100)]]
    });
  }

  ngOnInit(): void {
    this.cargarAreas();
  }

  cargarAreas(): void {
    this.cargando = true;

    this.areaService.listarAreas().subscribe({
      next: (data) => {
        this.areas = data;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar las áreas',
          life: 3000
        });
      }
    });
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.form.reset();
    this.mostrarFormulario = true;
  }

  editarArea(area: Area): void {
    this.editando = true;
    this.form.patchValue(area);
    this.mostrarFormulario = true;
  }

  guardarArea(): void {
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
    const area: Area = this.form.value;

    if (this.editando) {
      this.areaService.editarArea(area).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Actualización exitosa',
            detail: 'El área ha sido actualizada correctamente',
            life: 3000
          });
          this.cargarAreas();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al actualizar',
            detail: err.error?.mensaje || 'No se pudo actualizar el área',
            life: 4000
          });
        }
      });
    } else {
      this.areaService.registrarArea(area).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Registro exitoso',
            detail: 'El área ha sido registrada',
            life: 3000
          });
          this.cargarAreas();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al registrar',
            detail: err.error?.mensaje || 'No se pudo registrar el área',
            life: 4000
          });
        }
      });
    }
  }
  eliminarArea(id: number): void {
    this.areaService.eliminarArea(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Eliminación exitosa',
          detail: 'La categoría ha sido eliminada correctamente',
          life: 3000
        });
        this.cargarAreas();
      },
      error: (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error al eliminar',
          detail: err.error?.mensaje || 'No se pudo eliminar la categoría. Puede que tenga productos asociados.',
          life: 4000
        });
      }
    });
  }
  confirmarEliminar(area: Area): void {
      this.confirmationService.confirm({
        message: `¿Está seguro de eliminar la categoría "${area.nombre}"? Esta acción no se puede deshacer.`,
        header: 'Confirmar Eliminación',
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: 'Sí, eliminar',
        rejectLabel: 'Cancelar',
        acceptButtonStyleClass: 'p-button-danger',
        accept: () => {
          this.eliminarArea(area.id!);
        }
      });
    }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.editando = false;
    this.form.reset();
  }
}
