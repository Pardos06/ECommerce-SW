import { Routes } from '@angular/router';
import { LoginPage } from './features/public/login/login';
import { RegisterPage } from './features/public/register/register';
import { AuthGuard } from './core/guards/auth.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { ClientGuard } from './core/guards/client.guard';
import { HomePage } from './features/public/home/home';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePage },
  { path: 'login', component: LoginPage },
  { path: 'register', component: RegisterPage },

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
