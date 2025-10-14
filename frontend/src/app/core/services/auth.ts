import { RegistrarClienteRequest } from './../interfaces/registrar-cliente.request';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthRequest } from '../interfaces/auth.request';
import { Observable } from 'rxjs';
import { AuthResponse } from '../interfaces/auth.response';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root',
})
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

  cerrarSesion(): void {
    localStorage.removeItem('jwt_token');
  }
}
