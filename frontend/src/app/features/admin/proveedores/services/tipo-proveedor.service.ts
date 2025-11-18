import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { TipoProveedor } from '../interfaces/tipo-proveedor';
@Injectable({
  providedIn: 'root'
})
export class TipoProveedorService {
  private apiUrl = environment.apiUrl + '/tipos-proveedor';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwt_token') || '';
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  listarTipos(): Observable<TipoProveedor[]> {
    return this.http.get<TipoProveedor[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  obtenerPorId(id: number): Observable<TipoProveedor> {
    return this.http.get<TipoProveedor>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  registrarTipo(tipo: TipoProveedor): Observable<TipoProveedor> {
    return this.http.post<TipoProveedor>(this.apiUrl, tipo, { headers: this.getAuthHeaders() });
  }

  editarTipo(tipo: TipoProveedor): Observable<TipoProveedor> {
    return this.http.put<TipoProveedor>(this.apiUrl, tipo, { headers: this.getAuthHeaders() });
  }

  eliminarTipo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  buscarPorNombre(nombre: string): Observable<TipoProveedor[]> {
    return this.http.get<TipoProveedor[]>(`${this.apiUrl}/search/${encodeURIComponent(nombre)}`, { headers: this.getAuthHeaders() });
  }
}