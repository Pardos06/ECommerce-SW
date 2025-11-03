import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Auth } from '../services/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(private auth: Auth, private router: Router) {}

  canActivate(): boolean {
    const token = this.auth.obtenerToken();
    console.log('[AuthGuard] Token detectado:', token);

    if (token) {
      console.log('[AuthGuard] Permitiendo acceso');
      return true;
    }

    console.warn('[AuthGuard] Token ausente → redirigiendo a login');
    this.router.navigate(['/login']);
    return false;
  }

  // Delegamos canActivateChild a canActivate para que funcione si se usa canActivateChild en rutas
  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log('[AuthGuard] canActivateChild -> url solicitada:', state.url);
    return this.canActivate();
  }
}
