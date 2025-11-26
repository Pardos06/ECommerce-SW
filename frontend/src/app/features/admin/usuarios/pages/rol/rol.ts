import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Rol } from '../../interfaces/rol';
import { RolService } from '../../services/rol.service';
import { CommonModule } from '@angular/common';
import { MessageService, ConfirmationService } from 'primeng/api';
import { PrimeImportsModule } from '../../../../../prime-imports';

@Component({
  selector: 'app-rol-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './rol.html',
  styleUrls: ['./rol.scss']
})
export class RolPage implements OnInit {
  roles: Rol[] = [];
  cargando = true;
  mostrarFormulario = false;
  editando = false;
  guardando = false;
  form: FormGroup;

  constructor(
    private rolService: RolService,
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
    this.cargarRoles();
  }

  cargarRoles(): void {
    this.cargando = true;
    this.rolService.listarRoles().subscribe({
      next: data => {
        this.roles = data;
        this.cargando = false;
      },
      error: err => {
        console.error('Error al cargar roles', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar los roles',
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

  editarRol(rol: Rol): void {
    this.editando = true;
    this.form.patchValue(rol);
    this.mostrarFormulario = true;
  }

  guardarRol(): void {
    if (this.form.invalid) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Formulario incompleto',
        detail: 'Complete correctamente todos los campos',
        life: 3000
      });
      return;
    }

    this.guardando = true;
    const rol: Rol = this.form.value;

    if (this.editando) {
      this.rolService.editarRol(rol).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Actualización exitosa',
            detail: 'El rol ha sido actualizado correctamente',
            life: 3000
          });
          this.cargarRoles();
          this.cancelar();
        },
        error: err => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al actualizar',
            detail: err.error?.mensaje || 'No se pudo actualizar el rol',
            life: 4000
          });
        }
      });
    } else {
      this.rolService.registrarRol(rol).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Registro exitoso',
            detail: 'El rol ha sido creado correctamente',
            life: 3000
          });
          this.cargarRoles();
          this.cancelar();
        },
        error: err => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al crear',
            detail: err.error?.mensaje || 'No se pudo crear el rol',
            life: 4000
          });
        }
      });
    }
  }

  confirmarEliminar(rol: Rol): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el rol "${rol.nombre}"? Esta acción no se puede deshacer.`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.eliminarRol(rol.id!);
      }
    });
  }

  eliminarRol(id: number): void {
    this.rolService.eliminarRol(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Eliminación exitosa',
          detail: 'El rol ha sido eliminado correctamente',
          life: 3000
        });
        this.cargarRoles();
      },
      error: err => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error al eliminar',
          detail: err.error?.mensaje || 'No se pudo eliminar el rol. Puede estar asociado a usuarios.',
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
