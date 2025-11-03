import { Component, OnInit } from '@angular/core';
import { MetodoPago } from '../../interfaces/metodo.pago';
import { MetodoPagoService } from '../../services/metodo.pago';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-metodo-pago',
  imports: [CommonModule, FormsModule],
  templateUrl: './metodo-pago.html',
  styleUrl: './metodo-pago.scss'
})
export class MetodoPagoPage implements OnInit{
  metodos: MetodoPago[] = [];
  metodo: MetodoPago = { nombre: '' };
  editando: boolean = false;

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

  guardar(): void {
    if (this.editando) {
      this.metodoPagoService.editar(this.metodo).subscribe(() => {
        this.listarMetodos();
        this.cancelar();
      });
    } else {
      this.metodoPagoService.registrar(this.metodo).subscribe(() => {
        this.listarMetodos();
        this.cancelar();
      });
    }
  }

  editar(m: MetodoPago): void {
    this.metodo = { ...m };
    this.editando = true;
  }

  eliminar(id: number): void {
    if (confirm('¿Estás seguro de eliminar este método de pago?')) {
      this.metodoPagoService.eliminar(id).subscribe(() => this.listarMetodos());
    }
  }

  cancelar(): void {
    this.metodo = { nombre: '' };
    this.editando = false;
  }
}
