import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Auth } from '../services/auth';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  constructor(private auth: Auth, private router: Router) {}

  canActivate(): boolean {
  const rol = this.auth.obtenerRol();
  console.log('[AdminGuard] rol raw:', rol);
  const normalized = rol ? rol.toString().toLowerCase() : null;
  console.log('[AdminGuard] rol normalizado:', normalized);
  if (normalized === 'administrador' || normalized === 'admin') {
    return true;
  }
  console.log('[AdminGuard] no autorizado -> redirigiendo');
  this.router.navigate(['/login']);
  return false;
  }
}
