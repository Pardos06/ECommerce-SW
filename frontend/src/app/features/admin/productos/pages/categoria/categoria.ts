import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Categoria } from '../../interfaces/categoria';
import { CategoriaService } from '../../services/categoria';
import { CommonModule } from '@angular/common';
import { MessageService, ConfirmationService } from 'primeng/api';
import { PrimeImportsModule } from '../../../../../prime-imports';

@Component({
  selector: 'app-categoria-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './categoria.html',
  styleUrl: './categoria.scss'
})
export class CategoriaPage implements OnInit {
  categorias: Categoria[] = [];
  cargando: boolean = true;
  mostrarFormulario: boolean = false;
  editando: boolean = false;
  guardando: boolean = false;
  form: FormGroup;

  constructor(
    private categoriaService: CategoriaService,
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
    this.cargarCategorias();
  }

  cargarCategorias(): void {
    this.cargando = true;
    this.categoriaService.listarCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar categorías', err);
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar las categorías',
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

  editarCategoria(categoria: Categoria): void {
    this.editando = true;
    this.form.patchValue(categoria);
    this.mostrarFormulario = true;
  }

  guardarCategoria(): void {
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
    const categoria: Categoria = this.form.value;

    if (this.editando) {
      // Actualizar categoría existente
      this.categoriaService.editarCategoria(categoria).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Actualización exitosa',
            detail: 'La categoría ha sido actualizada correctamente',
            life: 3000
          });
          this.cargarCategorias();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al actualizar',
            detail: err.error?.mensaje || 'No se pudo actualizar la categoría',
            life: 4000
          });
        }
      });
    } else {
      // Crear nueva categoría
      this.categoriaService.registrarCategoria(categoria).subscribe({
        next: () => {
          this.guardando = false;
          this.messageService.add({
            severity: 'success',
            summary: 'Registro exitoso',
            detail: 'La categoría ha sido creada correctamente',
            life: 3000
          });
          this.cargarCategorias();
          this.cancelar();
        },
        error: (err) => {
          this.guardando = false;
          this.messageService.add({
            severity: 'error',
            summary: 'Error al crear',
            detail: err.error?.mensaje || 'No se pudo crear la categoría',
            life: 4000
          });
        }
      });
    }
  }

  confirmarEliminar(categoria: Categoria): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar la categoría "${categoria.nombre}"? Esta acción no se puede deshacer.`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: 'Sí, eliminar',
      rejectLabel: 'Cancelar',
      acceptButtonStyleClass: 'p-button-danger',
      accept: () => {
        this.eliminarCategoria(categoria.id!);
      }
    });
  }

  eliminarCategoria(id: number): void {
    this.categoriaService.eliminarCategoria(id).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Eliminación exitosa',
          detail: 'La categoría ha sido eliminada correctamente',
          life: 3000
        });
        this.cargarCategorias();
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

  cancelar(): void {
    this.mostrarFormulario = false;
    this.form.reset();
    this.editando = false;
  }
}
