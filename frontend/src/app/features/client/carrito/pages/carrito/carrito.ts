import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { CartService } from '../../../carrito/services/cart.service';
import { CartItem } from '../../../carrito/interfaces/cart-item';
import { Header } from '../../../../../shared/components/header/header';
import { Footer } from '../../../../../shared/components/footer/footer';
import { MetodoPagoClienteService } from '../../services/metodo-pago-cliente.service';
import { OrdenService } from '../../../ordenes/services/orden.service';
@Component({
  selector: 'app-carrito',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    Header,
    Footer
  ],
  templateUrl: './carrito.html',
  styleUrls: ['./carrito.scss']
})
export class CarritoPage implements OnInit {
  items: CartItem[] = [];
  total = 0;
  selectedMetodoPago: number = 0;
  metodosPago: any[] = [];

  constructor(
    public cartService: CartService,
    private metodoPagoClienteService: MetodoPagoClienteService,
    private ordenService: OrdenService
  ) {}

  ngOnInit() {
    this.metodoPagoClienteService.listar().subscribe({
      next: (data) => this.metodosPago = data
    });
    this.cartService.items$.subscribe(data => {
      this.items = data;
      this.total = this.cartService.getTotal();
    });
  }

  actualizar(item: CartItem) {
    this.cartService.updateCantidad(item.idProducto, item.cantidad);
  }

  eliminar(id: number) {
    this.cartService.removeItem(id);
  }
  procesarPago() {
  if (this.items.length === 0) {
    alert("El carrito está vacío.");
    return;
  }

  if (!this.selectedMetodoPago) {
    alert("Seleccione un método de pago.");
    return;
  }

  const clienteId = Number(localStorage.getItem("clienteId"));
  if (!clienteId) {
    alert("Debe iniciar sesión para continuar.");
    return;
  }

  const orden = {
    fechaOrden: new Date().toLocaleString("sv-SE").replace(" ", "T"),
    estado: "Procesando",
    estadoEmail: "Pendiente",
    clienteId: clienteId,
    metodoPagoId: this.selectedMetodoPago
  };

  const detalles = this.items.map(item => ({
    cantidad: item.cantidad,
    precioUnitario: item.precio,
    productoId: item.idProducto
  }));

  this.ordenService.crearOrden({ orden, detalles }).subscribe({
    next: () => {
      alert("Orden creada correctamente.");
      this.cartService.clear();
    },
    error: (err) => {
      console.error(err);
      alert("Ocurrió un error al procesar el pago.");
    }
  });
}

}
