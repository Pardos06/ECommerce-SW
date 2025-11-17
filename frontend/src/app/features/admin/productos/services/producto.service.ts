import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../interfaces/producto';
import { environment } from '../../../../environment/environment';
import { ProductoForm } from '../interfaces/producto-form';

@Injectable({
  providedIn: 'root',
})
export class ProductoService {
  private apiUrl = environment.apiUrl + '/productos';

  constructor(private http: HttpClient) {}

  listarProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  obtenerProductoPorId(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  crearProducto(producto: ProductoForm): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  actualizarProducto(producto: ProductoForm): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${producto.id}`, producto);
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
