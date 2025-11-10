import { Component, OnInit } from '@angular/core';
import { MetodoPago } from '../../interfaces/metodo.pago';
import { MetodoPagoService } from '../../services/metodo.pago';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PrimeImportsModule } from '../../../../../prime-imports';

@Component({
  selector: 'app-metodo-pago',
  imports: [CommonModule, FormsModule,  PrimeImportsModule],
  templateUrl: './metodo-pago.html',
  styleUrl: './metodo-pago.scss'
})
export class MetodoPagoPage implements OnInit{
  metodos: MetodoPago[] = [];
  metodo: MetodoPago = { nombre: '' };
  editando: boolean = false;
  visible: boolean = false;

  constructor(private metodoPagoService: MetodoPagoService) {}

  ngOnInit(): void {
    this.listarMetodos();
  }

  listarMetodos(): void {
    this.metodoPagoService.listar().subscribe({
      next: (data) => (this.metodos = data),
      error: (err) => console.error(err)
    });
  }
    showDialog(): void {
    this.visible = true;
    this.metodo = { nombre: '' };
    this.editando = false;
  }

  guardar(): void {
    const request = this.editando
      ? this.metodoPagoService.editar(this.metodo)
      : this.metodoPagoService.registrar(this.metodo);

    request.subscribe({
      next: () => {
        this.listarMetodos();
        this.cancelar();
      },
      error: (err) => console.error('Error al guardar método de pago:', err)
    });
  }

  editar(m: MetodoPago): void {
    this.metodo = { ...m };
    this.editando = true;
    this.visible = true;
  }

  eliminar(id: number): void {
    if (confirm('¿Estás seguro de eliminar este método de pago?')) {
      this.metodoPagoService.eliminar(id).subscribe({
        next: () => this.listarMetodos(),
        error: (err) => console.error('Error al eliminar método de pago:', err)
      });
    }
  }

  cancelar(): void {
    this.metodo = { nombre: '' };
    this.editando = false;
    this.visible = false;
  }
}
