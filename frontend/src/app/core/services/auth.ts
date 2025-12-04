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
    localStorage.setItem('token', token);
  }

  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  guardarRol(rol: string): void {
    if (!rol) return;
    // Normalizar a formato con primera letra mayúscula: "Administrador" o "Cliente"
    const normalizado = rol.charAt(0).toUpperCase() + rol.slice(1).toLowerCase();
    localStorage.setItem('rol', normalizado);
  }

  obtenerRol(): string | null {
    return localStorage.getItem('rol');
  }

  guardarClienteId(id: number): void {
    localStorage.setItem('clienteId', String(id));
  }

  obtenerClienteId(): number | null {
    const id = localStorage.getItem('clienteId');
    return id ? Number(id) : null;
  }
    cerrarSesion(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('rol');
    localStorage.removeItem('clienteId');
    localStorage.removeItem('nombre');
  }
  
}
