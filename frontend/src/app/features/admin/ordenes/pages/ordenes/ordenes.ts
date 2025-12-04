import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { OrdenAdminService } from '../../services/orden-admin.service';
import { MessageService } from 'primeng/api';
import { Orden } from '../../interfaces/orden';
import { FormsModule } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-ordenes',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TableModule,
    ButtonModule,
    DialogModule,
    ToastModule,
  ],
  providers: [MessageService],
  templateUrl: './ordenes.html',
  styleUrl: './ordenes.scss',
})
export class OrdenesAdminPage implements OnInit {

  ordenes: Orden[] = [];
  cargando = true;

  ordenSeleccionada: Orden | null = null;
  mostrarModalDetalles = false;

  estados = [
    { label: 'Pendiente', value: 'Pendiente' },
    { label: 'Procesando', value: 'Procesando' },
    { label: 'Enviado', value: 'Enviado' },
    { label: 'Completado', value: 'Completado' },
    { label: 'Cancelado', value: 'Cancelado' }
  ];

  constructor(
    private ordenService: OrdenAdminService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.cargarOrdenes();
  }

  cargarOrdenes() {
    this.ordenService.listarOrdenes().subscribe({
      next: data => {
        this.ordenes = data;
        this.cargando = false;
      },
      error: () => {
        this.cargando = false;
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar las órdenes'
        });
      }
    });
  }

  // ★ NUEVO
  verDetalles(orden: Orden) {
    this.ordenSeleccionada = orden;
    this.mostrarModalDetalles = true;
  }

  // ★ NUEVO
  cerrarModal() {
    this.mostrarModalDetalles = false;
    this.ordenSeleccionada = null;
  }

  actualizarEstado(orden: Orden) {
    this.ordenService.actualizarEstado(orden.id, orden.estado).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Estado actualizado',
          detail: 'El cliente verá el cambio'
        });
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudo actualizar el estado'
        });
      }
    });
  }
}
