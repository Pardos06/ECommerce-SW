import { Routes } from '@angular/router';
import { DashboardClient } from './dashboard-client/dashboard-client';

export const CLIENTE_ROUTES: Routes = [
  {
    path: '',
    component: DashboardClient,
    children: [
      { path: '', redirectTo: 'productos', pathMatch: 'full' },
    ],
  },
];
