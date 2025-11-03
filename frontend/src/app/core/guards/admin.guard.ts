import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Auth } from '../services/auth';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate, CanActivateChild {
  constructor(private auth: Auth, private router: Router) {}

  canActivate(): boolean {
    const rol = this.auth.obtenerRol();
    console.log('[AdminGuard] rol obtenido:', rol);

    const normalized = rol ? rol.toUpperCase() : null;
    console.log('[AdminGuard] rol normalizado:', normalized);

    if (normalized && ['ADMINISTRADOR', 'ADMIN'].includes(normalized)) {
      console.log('[AdminGuard] Permitiendo acceso por rol');
      return true;
    }

    console.log('[AdminGuard] acceso denegado -> redirigiendo');
    this.router.navigate(['/login']);
    return false;
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('[AdminGuard] canActivateChild -> url solicitada:', state.url);
    return this.canActivate();
  }
}
