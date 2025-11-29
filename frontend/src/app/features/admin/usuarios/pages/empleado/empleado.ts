import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Empleado } from '../../interfaces/empleado';
import { EmpleadoForm } from '../../interfaces/empleado-form';
import { EmpleadoService } from '../../services/empleado.service';
import { UsuarioService } from '../../services/usuario.service';
import { AreaService } from '../../../rr_hh/services/area.service';
import { CargoService } from '../../../rr_hh/services/cargo.service';
import { PrimeImportsModule } from '../../../../../prime-imports';
import { MessageService, ConfirmationService } from 'primeng/api';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-empleado',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './empleado.html',
  styleUrl: './empleado.scss'
})
export class EmpleadoPage implements OnInit {

  empleados: Empleado[] = [];
  usuarios: { id: number; nombre: string; email: string }[] = [];
  areas: { id: number; nombre: string }[] = [];
  cargos: { id: number; nombre: string }[] = [];
  form: FormGroup;
  cargando = true;
  editando = false;
  mostrarFormulario = false;
  guardando = false;

  constructor(
    private readonly empleadoService: EmpleadoService,
    private readonly usuarioService: UsuarioService,
    private readonly areaService: AreaService,
    private readonly cargoService: CargoService,
    private readonly fb: FormBuilder,
    private readonly messageService: MessageService,
    private readonly confirmationService: ConfirmationService
  ) {
    this.form = this.fb.group({
      id: [0],
      usuarioId: [0, [Validators.required, Validators.min(1)]],
      areaId: [0, [Validators.required, Validators.min(1)]],
      cargoId: [0, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    this.cargarEmpleados();
    this.cargarUsuarios();
    this.cargarAreas();
    this.cargarCargos();
  }

  cargarEmpleados(): void {
    this.cargando = true;
    this.empleadoService.listarEmpleados().subscribe({
      next: data => {
        this.empleados = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.cargando = false;
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los empleados', 
          life: 3000 
        });
      }
    });
  }

  cargarUsuarios(): void {
    this.usuarioService.listarUsuarios().subscribe({
      next: data => {
        // Mapear solo id, nombre y email
        this.usuarios = data.map(u => ({
          id: u.id,
          nombre: u.nombre,
          email: u.email
        }));
      },
      error: err => {
        console.error('No se pudieron cargar los usuarios', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los usuarios', 
          life: 3000 
        });
      }
    });
  }

  cargarAreas(): void {
    this.areaService.listarAreas().subscribe({
      next: data => {
        this.areas = data
          .filter((a): a is { id: number; nombre: string; descripcion: string } => a.id !== null)
          .map(a => ({ id: a.id, nombre: a.nombre }));
      },
      error: err => {
        console.error('No se pudieron cargar las áreas', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar las áreas', 
          life: 3000 
        });
      }
    });
  }

  cargarCargos(): void {
    this.cargoService.listarCargos().subscribe({
      next: data => {
        this.cargos = data
          .filter((c): c is { id: number; nombre: string; descripcion: string } => c.id !== null)
          .map(c => ({ id: c.id, nombre: c.nombre }));
      },
      error: err => {
        console.error('No se pudieron cargar los cargos', err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: 'No se pudieron cargar los cargos', 
          life: 3000 
        });
      }
    });
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.form.reset({ id: 0, usuarioId: 0, areaId: 0, cargoId: 0 });
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
      const empleadoForm: EmpleadoForm = {
        id: this.editando ? this.form.value.id : undefined,
        usuarioId: this.form.value.usuarioId,
        areaId: this.form.value.areaId,
        cargoId: this.form.value.cargoId
      };

      if (this.editando) {
        await firstValueFrom(this.empleadoService.actualizarEmpleado(empleadoForm));
      } else {
        await firstValueFrom(this.empleadoService.registrarEmpleado(empleadoForm));
      }

      this.guardando = false;
      this.messageService.add({
        severity: 'success',
        summary: this.editando ? 'Actualización exitosa' : 'Registro exitoso',
        detail: `El empleado ha sido ${this.editando ? 'actualizado' : 'registrado'} correctamente`,
        life: 3000
      });

      this.cargarEmpleados();
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
          detail: err.error?.mensaje || err.error?.message || 'Ocurrió un error al guardar el empleado',
          life: 5000
        });
      }
    }
  }

  editar(empleado: Empleado): void {
    this.form.patchValue({
      id: empleado.id,
      usuarioId: empleado.usuarioId,
      areaId: this.buscarIdPorNombre(this.areas, empleado.area),
      cargoId: this.buscarIdPorNombre(this.cargos, empleado.cargo)
    });
    this.editando = true;
    this.mostrarFormulario = true;
  }

  buscarIdPorNombre(lista: { id: number; nombre: string }[], nombre: string | null): number {
    if (!nombre) return 0;
    const item = lista.find(i => i.nombre === nombre);
    return item ? item.id : 0;
  }

  confirmarEliminar(empleado: Empleado): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar al empleado "${empleado.nombreUsuario}"? Esto solo desvinculará al usuario del área y cargo.`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.eliminarEmpleado(empleado.id)
    });
  }

  eliminarEmpleado(id: number): void {
    this.empleadoService.eliminarEmpleado(id).subscribe({
      next: () => {
        this.messageService.add({ 
          severity: 'success', 
          summary: 'Eliminación exitosa', 
          detail: 'El empleado ha sido eliminado correctamente', 
          life: 3000 
        });
        this.cargarEmpleados();
      },
      error: err => {
        console.error(err);
        this.messageService.add({ 
          severity: 'error', 
          summary: 'Error', 
          detail: err.error?.mensaje || 'No se pudo eliminar el empleado', 
          life: 3000 
        });
      }
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.form.reset({ id: 0, usuarioId: 0, areaId: 0, cargoId: 0 });
    this.editando = false;
  }
}
