import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { Producto } from '../interfaces/producto';

@Injectable({
  providedIn: 'root',
})
export class ProductoClientService {

  private apiUrl = `${environment.apiUrl}/productos`;
  private imageUrl = environment.imageUrl;

  constructor(private http: HttpClient) {}

  listarProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  obtenerProductoPorId(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  buscarProductosDisponibles(nombre: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.apiUrl}/search/${nombre}`);
  }

  obtenerUrlImagen(nombreArchivo: string): string {
    if (!nombreArchivo) return '';
    return `${this.imageUrl}/${nombreArchivo}`;
  }
}
