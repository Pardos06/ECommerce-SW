import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { Orden } from '../interfaces/orden';

@Injectable({
  providedIn: 'root'
})
export class OrdenService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  /**
   * Listar todas las órdenes
   */
  listarOrdenes(): Observable<Orden[]> {
    return this.http.get<Orden[]>(`${this.apiUrl}/ordenes`);
  }

  /**
   * Obtener una orden por ID
   */
  obtenerOrdenPorId(id: number): Observable<Orden> {
    return this.http.get<Orden>(`${this.apiUrl}/ordenes/${id}`);
  }

  /**
   * Listar órdenes por cliente
   */
  listarOrdenesPorCliente(clienteId: number): Observable<Orden[]> {
    return this.http.get<Orden[]>(`${this.apiUrl}/ordenes/cliente/${clienteId}`);
  }

  /**
   * Crear una nueva orden
   */
  crearOrden(orden: Partial<Orden>): Observable<Orden> {
    return this.http.post<Orden>(`${this.apiUrl}/ordenes`, orden);
  }

  /**
   * Actualizar una orden existente
   */
  actualizarOrden(orden: Orden): Observable<Orden> {
    return this.http.put<Orden>(`${this.apiUrl}/ordenes/${orden.id}`, orden);
  }

  /**
   * Actualizar el estado de una orden
   */
  actualizarEstadoOrden(id: number, estado: string): Observable<Orden> {
    return this.http.patch<Orden>(`${this.apiUrl}/ordenes/${id}/estado`, { estado });
  }

  /**
   * Eliminar una orden
   */
  eliminarOrden(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/ordenes/${id}`);
  }

  /**
   * Obtener estadísticas de órdenes (opcional, si el backend lo soporta)
   */
  obtenerEstadisticas(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/ordenes/estadisticas`);
  }
}
