import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { Proveedor } from '../interfaces/proveedor';
import { ProveedorForm } from '../interfaces/proveedor.form';

@Injectable({
  providedIn: 'root'
})
export class ProveedorService {
  private apiUrl = environment.apiUrl + '/proveedores';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwt_token') || '';
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  listarProveedores(): Observable<Proveedor[]> {
    return this.http.get<Proveedor[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  obtenerPorId(id: number): Observable<Proveedor> {
    return this.http.get<Proveedor>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  registrarProveedor(request: ProveedorForm): Observable<Proveedor> {
    return this.http.post<Proveedor>(this.apiUrl, request, { headers: this.getAuthHeaders() });
  }

  editarProveedor(request: ProveedorForm): Observable<Proveedor> {
    return this.http.put<Proveedor>(this.apiUrl, request, { headers: this.getAuthHeaders() });
  }

  eliminarProveedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  buscarPorTipo(tipoProveedor: string): Observable<Proveedor[]> {
    return this.http.get<Proveedor[]>(`${this.apiUrl}/search/${encodeURIComponent(tipoProveedor)}`, {
      headers: this.getAuthHeaders()
    });
  }
}
