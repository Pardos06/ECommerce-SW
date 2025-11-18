import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../interfaces/producto';
import { ProductoForm } from '../interfaces/producto-form';
import { environment } from '../../../../environment/environment';
import { ProductoImagenResponse } from '../interfaces/producto-imagen-response';

@Injectable({
  providedIn: 'root',
})
export class ProductoService {
  private apiUrl = environment.apiUrl + '/productos';

  constructor(private http: HttpClient) {}
  
  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwt_token') || '';
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  listarProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  obtenerProductoPorId(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  buscarProductosDisponibles(nombre: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/search/${nombre}`, { headers: this.getAuthHeaders() });
  }
  crearProducto(producto: ProductoForm): Observable<Producto> {
  return this.http.post<Producto>(this.apiUrl, producto, { headers: this.getAuthHeaders() });
  }

  actualizarProducto(producto: ProductoForm): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}`, producto, { headers: this.getAuthHeaders() });
  }
    subirImagen(archivo: File): Observable<ProductoImagenResponse> {
    const formData = new FormData();
    formData.append('archivo', archivo);
    return this.http.post<ProductoImagenResponse>(`${this.apiUrl}/imagenes`, formData, { headers: this.getAuthHeaders() });
  }
}
