import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OrdenRequest } from '../interfaces/orden-request';
import { OrdenDetailsRequest } from '../interfaces/orden-details-request';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})

export class OrdenService {

  private apiUrl = `${environment.apiUrl}/ordenes`;

  constructor(private http: HttpClient) {}

  crearOrden(request: { orden: OrdenRequest, detalles: OrdenDetailsRequest[] }): Observable<any> {
    return this.http.post(`${this.apiUrl}`, request);
  }
  listarOrdenesPorCliente(clienteId: number): Observable<any[]> {
   return this.http.get<any[]>(`${this.apiUrl}/por-cliente/${clienteId}`);
  }

}
