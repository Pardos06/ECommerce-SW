import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Cliente } from '../../interfaces/cliente';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { ClienteService } from '../../services/cliente.service';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-clientes-admin',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    DialogModule,
    ButtonModule,
    ToastModule
  ],
  providers: [MessageService],
  templateUrl: './clientes.html',
  styleUrls: ['./clientes.scss']
})
export class ClientePage implements OnInit {

  clientes: Cliente[] = [];
  cargando = true;

  constructor(
    private clienteService: ClienteService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.cargarClientes();
  }

  cargarClientes() {
    this.clienteService.listarClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.cargando = false;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo cargar la lista de clientes' });
        this.cargando = false;
      }
    });
  }

  verCliente(cliente: Cliente) {
    console.log("Cliente seleccionado:", cliente);
  }
}
