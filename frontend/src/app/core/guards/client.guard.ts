import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Auth } from '../services/auth';

@Injectable({ providedIn: 'root' })
export class ClientGuard implements CanActivate {
  constructor(private auth: Auth, private router: Router) {}

  canActivate(): boolean {
    const rol = localStorage.getItem('rol')?.toUpperCase();
    console.log('[ClientGuard] Rol detectado:', rol);

    if (rol !== 'CLIENTE') {
      console.warn('[ClientGuard] Acceso denegado, redirigiendo a login');
      this.router.navigate(['/login']);
      return false;
    }

    return true;
}
}
