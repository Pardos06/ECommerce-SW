import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Usuario } from '../../interfaces/usuario';
import { UsuarioForm } from '../../interfaces/usuario-form';
import { UsuarioCreateForm } from '../../interfaces/usuario-create-form';
import { UsuarioService } from '../../services/usuario.service';
import { RolService } from '../../services/rol.service';
import { PrimeImportsModule } from '../../../../../prime-imports';
import { MessageService, ConfirmationService } from 'primeng/api';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-usuarios-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './usuario.html',
  styleUrls: ['./usuario.scss'],
})
export class UsuariosPage implements OnInit {

  usuarios: Usuario[] = [];
  roles: { id: number; nombre: string }[] = [];
  form: FormGroup;
  cargando = true;
  editando = false;
  mostrarFormulario = false;
  guardando = false;

  constructor(
    private usuarioService: UsuarioService,
    private rolService: RolService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.form = this.fb.group({
      id: [0],
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]], // Obligatorio al crear, se ajustará al editar
      estado: ['', Validators.required],
      rolId: [0, Validators.required]
    });
  }

  ngOnInit(): void {
    this.cargarUsuarios();
    this.cargarRoles();
  }

  cargarUsuarios(): void {
    this.cargando = true;
    this.usuarioService.listarUsuarios().subscribe({
      next: data => {
        this.usuarios = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.cargando = false;
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudieron cargar los usuarios', life: 3000 });
      }
    });
  }

  cargarRoles(): void {
    this.rolService.listarRoles().subscribe({
      next: data => this.roles = data,
      error: err => {
        console.error('No se pudieron cargar los roles', err);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudieron cargar los roles', life: 3000 });
      }
    });
  }

  buscarRolIdPorNombre(nombre: string): number {
    const rol = this.roles.find(r => r.nombre === nombre);
    return rol ? rol.id : 0;
  }

  mostrarDialogo(): void {
    this.editando = false;
    this.form.reset({ id: 0, nombre: '', email: '', password: '', estado: '', rolId: 0 });
    // Al crear, el password es obligatorio
    this.form.get('password')?.setValidators([Validators.required, Validators.minLength(6)]);
    this.form.get('password')?.updateValueAndValidity();
    this.mostrarFormulario = true;
  }

  async guardar(): Promise<void> {
    if (this.form.invalid) {
      Object.keys(this.form.controls).forEach(key => this.form.get(key)?.markAsTouched());
      this.messageService.add({ severity: 'warn', summary: 'Formulario incompleto', detail: 'Complete los campos obligatorios', life: 3000 });
      return;
    }

    this.guardando = true;

    try {
      if (this.editando) {
        // Solo incluir password si el usuario lo ingresó
        const usuarioForm: UsuarioForm = {
          id: this.form.value.id,
          nombre: this.form.value.nombre,
          email: this.form.value.email,
          estado: this.form.value.estado,
          rolId: this.form.value.rolId,
          passwordHash: this.form.value.password ? this.form.value.password : '' // backend debe aceptar vacío
        };

        await firstValueFrom(this.usuarioService.actualizarUsuario(usuarioForm));
      } else {
        // Validar que el password no esté vacío al crear
        const password = this.form.value.password?.trim();
        if (!password) {
          this.guardando = false;
          this.messageService.add({ 
            severity: 'warn', 
            summary: 'Contraseña requerida', 
            detail: 'La contraseña es obligatoria para crear un usuario', 
            life: 3000 
          });
          return;
        }

        const usuarioData: UsuarioCreateForm = {
          nombre: this.form.value.nombre.trim(),
          email: this.form.value.email.trim(),
          passwordHash: password, // Enviar como passwordHash que el backend espera
          estado: this.form.value.estado,
          rolId: this.form.value.rolId
        };
        await firstValueFrom(this.usuarioService.crearUsuario(usuarioData));
      }

      this.guardando = false;
      this.messageService.add({
        severity: 'success',
        summary: this.editando ? 'Actualización exitosa' : 'Registro exitoso',
        detail: `El usuario ha sido ${this.editando ? 'actualizado' : 'creado'} correctamente`,
        life: 3000
      });

      this.cargarUsuarios();
      this.cancelar();

    } catch (err: any) {
      this.guardando = false;
      console.error(err);

      // Manejo específico de errores 403
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
          detail: err.error?.message || err.message || 'Ocurrió un error al guardar el usuario',
          life: 5000
        });
      }
    }
  }

  editar(usuario: Usuario): void {
    this.form.patchValue({
      id: usuario.id,
      nombre: usuario.nombre,
      email: usuario.email,
      password: '',
      estado: usuario.estado,
      rolId: this.buscarRolIdPorNombre(usuario.rol)
    });
    // Al editar, el password es opcional
    this.form.get('password')?.clearValidators();
    this.form.get('password')?.updateValueAndValidity();
    this.editando = true;
    this.mostrarFormulario = true;
  }

  confirmarEliminar(usuario: Usuario): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar al usuario "${usuario.nombre}"?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => this.eliminarUsuario(usuario.id)
    });
  }

  eliminarUsuario(id: number): void {
    this.usuarioService.eliminarUsuario(id).subscribe({
      next: () => {
        this.messageService.add({ severity: 'success', summary: 'Eliminación exitosa', detail: 'Usuario eliminado correctamente', life: 3000 });
        this.cargarUsuarios();
      },
      error: err => {
        console.error(err);
        if (err.status === 403) {
          this.messageService.add({ severity: 'error', summary: 'Acción no permitida', detail: 'No tiene permisos para eliminar este usuario', life: 5000 });
        } else {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo eliminar el usuario', life: 4000 });
        }
      }
    });
  }

  cancelar(): void {
    this.form.reset({ id: 0, nombre: '', email: '', password: '', estado: '', rolId: 0 });
    this.editando = false;
    this.mostrarFormulario = false;
  }
}
