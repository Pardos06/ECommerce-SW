import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Auth } from '../services/auth';

@Injectable({ providedIn: 'root' })
export class ClientGuard implements CanActivate {
  constructor(private auth: Auth, private router: Router) {}

  canActivate(): boolean {
    const rol = localStorage.getItem('rol');
    if (rol !== 'CLIENTE') {
      this.router.navigate(['/admin']);
      return false;
    }
    return true;
  }
}
