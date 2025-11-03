import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthRequest } from '../interfaces/auth.request';
import { AuthResponse } from '../interfaces/auth.response';
import { RegistrarClienteRequest } from '../interfaces/registrar-cliente.request';
import { environment } from '../../environment/environment';

@Injectable({ providedIn: 'root' })
export class Auth {
  private apiUrl = environment.apiUrl + '/auth';

  constructor(private http: HttpClient) {}

  login(request: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request);
  }

  register(request: RegistrarClienteRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, request);
  }

  guardarToken(token: string): void {
    localStorage.setItem('jwt_token', token);
  }

  obtenerToken(): string | null {
    return localStorage.getItem('jwt_token');
  }

  guardarRol(rol: string): void {
    if (!rol) return;
    localStorage.setItem('rol', rol.toString().toUpperCase());
  }

  obtenerRol(): string | null {
    const r = localStorage.getItem('rol');
    return r ? r.toUpperCase() : null;
  }

  cerrarSesion(): void {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('rol');
  }
}
