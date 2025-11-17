import { Routes } from '@angular/router';
import { LoginComponent } from './features/public/login/login';
import { RegisterComponent } from './features/public/register/register';
import { AuthGuard } from './core/guards/auth.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { ClientGuard } from './core/guards/client.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'register', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'admin',
    canActivate: [AuthGuard, AdminGuard],
    loadChildren: () =>
      import('./features/admin/admin.routes').then((m) => m.ADMIN_ROUTES),
  },
  {
    path: 'cliente',
    canActivate: [AuthGuard, ClientGuard],
    loadChildren: () =>
      import('./features/client/client.routes').then((m) => m.CLIENTE_ROUTES),
  },

  { path: '**', redirectTo: 'login' },
];
