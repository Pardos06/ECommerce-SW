import { Routes } from '@angular/router';
import { DashboardAdmin } from './dashboard-admin/dashboard-admin';
import { ProductosPage } from './productos/pages/productos/productos';
import { CategoriaPage } from './productos/pages/categoria/categoria';
import { MetodoPagoPage } from './compras/pages/metodo-pago/metodo-pago';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: DashboardAdmin,
    children: [
      { 
        path: '',
        redirectTo: 'categorias',
        pathMatch: 'full' 
      },

      { 
        path: 'productos',
        component: ProductosPage
      },

      { 
        path: 'categorias',
        component: CategoriaPage
      },

      { 
        path: 'metodos-pago',
        component: MetodoPagoPage 
      },
      
    ],
  },
];