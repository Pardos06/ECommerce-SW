import { Component, OnInit } from '@angular/core';
import { Proveedor } from '../../interfaces/proveedor';
import { ProveedorForm } from '../../interfaces/proveedor.form';
import { ProveedorService } from '../../services/proveedor.service';
import { TipoProveedorService } from '../../services/tipo-proveedor.service';
import { TipoProveedor } from '../../interfaces/tipo-proveedor';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PrimeImportsModule } from '../../../../../prime-imports';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-proveedor',
  standalone: true,
  templateUrl: './proveedor.html',
  styleUrls: ['./proveedor.scss'],
  imports: [CommonModule, ReactiveFormsModule, PrimeImportsModule,FormsModule],
  providers: [MessageService, ConfirmationService],
})
export class ProveedorPage implements OnInit {

  proveedores: Proveedor[] = [];
  tipos: TipoProveedor[] = [];
  proveedorDialog = false;
  eliminarDialog = false;

  proveedorForm: ProveedorForm = {
    id: 0,
    nombre: '',
    telefono: 0,
    email: '',
    direccion: '',
    tipoProveedorId: 0
  };

  selectedId = 0;

  constructor(
    private proveedorService: ProveedorService,
    private tipoProveedorService: TipoProveedorService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.cargarProveedores();
    this.cargarTiposProveedor();
  }

  cargarProveedores(): void {
    this.proveedorService.listarProveedores().subscribe({
      next: resp => this.proveedores = resp
    });
  }

  cargarTiposProveedor(): void {
    this.tipoProveedorService.listarTipos().subscribe({
      next: resp => this.tipos = resp
    });
  }

  abrirNuevo(): void {
    this.proveedorForm = {
      id: 0,
      nombre: '',
      telefono: 0,
      email: '',
      direccion: '',
      tipoProveedorId: 0
    };
    this.proveedorDialog = true;
  }

  editarProveedor(proveedor: Proveedor): void {
    this.proveedorForm = {
      id: proveedor.id,
      nombre: proveedor.nombre,
      telefono: Number(proveedor.telefono),
      email: proveedor.email,
      direccion: proveedor.direccion,
      tipoProveedorId: this.tipos.find(t => t.nombre === proveedor.tipoProveedor)?.id || 0
    };
    this.proveedorDialog = true;
  }

  guardarProveedor(): void {
    if (this.proveedorForm.id === 0) {
      this.registrar();
    } else {
      this.editar();
    }
  }

  registrar(): void {
    this.proveedorService.registrarProveedor(this.proveedorForm).subscribe({
      next: () => {
        this.cargarProveedores();
        this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Proveedor registrado.' });
        this.proveedorDialog = false;
      }
    });
  }

  editar(): void {
    this.proveedorService.editarProveedor(this.proveedorForm).subscribe({
      next: () => {
        this.cargarProveedores();
        this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Proveedor actualizado.' });
        this.proveedorDialog = false;
      }
    });
  }

  confirmarEliminar(id: number): void {
    this.selectedId = id;
    this.eliminarDialog = true;
  }

  eliminar(): void {
    this.proveedorService.eliminarProveedor(this.selectedId).subscribe({
      next: () => {
        this.cargarProveedores();
        this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Proveedor eliminado.' });
        this.eliminarDialog = false;
      }
    });
  }
}
