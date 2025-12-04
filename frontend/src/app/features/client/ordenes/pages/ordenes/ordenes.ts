import { Component, OnInit } from '@angular/core';
import { OrdenService } from '../../services/orden.service';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Header } from '../../../../../shared/components/header/header';
import { Footer } from '../../../../../shared/components/footer/footer';

@Component({
  selector: 'app-ordenes',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, Header, Footer],
  templateUrl: './ordenes.html',
  styleUrl: './ordenes.scss',
})
export class OrdenesPageClient implements OnInit {
  ordenes: any[] = [];
  clienteId!: number;

  constructor(
    public ordenService: OrdenService
  ) {}

  ngOnInit() {
    const cid = localStorage.getItem("clienteId");

    if (!cid) {
      console.error("Cliente no encontrado en localStorage");
      return;
    }

    this.clienteId = Number(cid);

    this.ordenService.listarOrdenesPorCliente(this.clienteId)
      .subscribe({
        next: (data) => {
          console.log("ÓRDENES DESDE EL BACKEND:", data);
          console.log("Órdenes cargadas:", data);
          this.ordenes = data;
        },
        error: (err) => {
          console.error("Error cargando órdenes:", err);
        }
      });
  }
  Total(detalles: { cantidad: number; precioUnitario: number }[]): number {
    return detalles.reduce((acc, d) => acc + d.cantidad * d.precioUnitario, 0);
  }

}
