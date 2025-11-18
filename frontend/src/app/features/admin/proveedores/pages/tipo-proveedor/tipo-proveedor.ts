// src/app/features/proveedor/tipo-proveedor/pages/tipo-proveedor.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MessageService, ConfirmationService } from 'primeng/api';

import { PrimeImportsModule } from '../../../../../prime-imports';
import { TipoProveedor } from '../../interfaces/tipo-proveedor';
import { TipoProveedorService } from '../../services/tipo-proveedor.service';

@Component({
  selector: 'app-tipo-proveedor-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './tipo-proveedor.html',
  styleUrl: './tipo-proveedor.scss'
})
export class TipoProveedorPage implements OnInit {
  
  tipos: TipoProveedor[] = [];
  cargando: boolean = true;
  mostrarFormulario: boolean = false;
  editando: boolean = false;
  guardando: boolean = false;
  form: FormGroup;

  constructor(
    private tipoProveedorService: TipoProveedorService,
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
    this.cargarTipos();
  }

  cargarTipos(): void {
    this.cargando = true;
    this.tipoProveedorService.listarTipos().subscribe({
      next: (data) => {
        this.tipos = data;
        this.cargando = false;
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al cargar los tipos de proveedor',
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

  editarRegistro(item: TipoProveedor): void {
    this.editando = true;
    this.form.patchValue(item);
    this.mostrarFormulario = true;
  }

  guardar(): void {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formulario incompleto',
        detail: 'Complete todos los campos correctamente'
      });
      return;
    }

    this.guardando = true;
    const tipo: TipoProveedor = this.form.value;

    if (this.editando) {
      this.tipoProveedorService.editarTipo(tipo).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Actualizado',
            detail: 'Tipo de proveedor actualizado correctamente'
          });
          this.cargarTipos();
          this.cancelar();
        },
        error: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No se pudo actualizar el registro'
          });
        }
      });

    } else {
      this.tipoProveedorService.registrarTipo(tipo).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Registrado',
            detail: 'Tipo de proveedor registrado correctamente'
          });
          this.cargarTipos();
          this.cancelar();
        },
        error: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error',
            detail: 'No se pudo registrar el tipo de proveedor'
          });
        }
      });
    }
  }

  confirmarEliminar(item: TipoProveedor): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el tipo "${item.nombre}"?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.eliminar(item.id!)
    });
  }

  eliminar(id: number): void {
    this.tipoProveedorService.eliminarTipo(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Eliminado',
          detail: 'Tipo eliminado correctamente'
        });
        this.cargarTipos();
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudo eliminar el tipo'
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
