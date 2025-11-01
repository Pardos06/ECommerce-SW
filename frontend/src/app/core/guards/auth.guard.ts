import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Auth } from '../services/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private auth: Auth, private router: Router) {}

  canActivate(): boolean {
  const token = this.auth.obtenerToken();
  console.log('[AuthGuard] token:', token);
  if (token) { return true; }
  console.log('[AuthGuard] no token -> redirigiendo a /login');
  this.router.navigate(['/login']);
  return false;
  }
}
