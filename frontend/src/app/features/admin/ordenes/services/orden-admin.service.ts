import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';

import { Orden } from '../interfaces/orden';

@Injectable({
  providedIn: 'root'
})
export class OrdenAdminService {

  private apiUrl = `${environment.apiUrl}/ordenes`;

  constructor(private http: HttpClient) {}

  listarOrdenes(): Observable<Orden[]> {
    return this.http.get<Orden[]>(`${this.apiUrl}`);
  }

  obtenerOrden(id: number): Observable<Orden> {
    return this.http.get<Orden>(`${this.apiUrl}/${id}`);
  }

  actualizarEstado(id: number, estado: string): Observable<Orden> {
  return this.http.put<Orden>(
    `${this.apiUrl}/actualizar-estado/${id}`, 
    estado,
    { headers: { "Content-Type": "text/plain" }}
  );
}
}
