import { Routes } from '@angular/router';
import { DashboardAdmin } from './dashboard-admin/dashboard-admin';
import { ProductosPage } from './productos/pages/productos-page/productos-page';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: DashboardAdmin,
    children: [
      { path: '', redirectTo: 'productos', pathMatch: 'full' },
      { path: 'productos', component: ProductosPage },
    ],
  },
];