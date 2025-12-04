import { Routes } from '@angular/router';
import { ProductosClientPage } from './productos/pages/productos/productos';
import { CarritoPage } from './carrito/pages/carrito/carrito';
import { DashboardClient } from './dashboard-client/dashboard-client';
import { OrdenesPageClient } from './ordenes/pages/ordenes/ordenes';

export const CLIENTE_ROUTES: Routes = [
  {
    path: '',
    children: [
      {
        path: 'productos',
        component: ProductosClientPage,
        title: 'Productos'
      },
      {
        path: 'carrito',
        component: CarritoPage,
        title: 'Carrito'
      },
      {
        path: 'dashboard',
        component: DashboardClient,
        title: 'Panel del cliente'
      },
      {
        path: 'mis-ordenes',
        component: OrdenesPageClient,
        title: 'Mis Órdenes'
      }
    ]
  }
];
