import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CompraDetails } from '../interfaces/compra.details';
import { CompraDetailForm } from '../interfaces/compra.detail.form';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CompraDetailsService {
  private readonly apiUrl = environment.apiUrl + '/compra-detalle';

  constructor(private readonly http: HttpClient) {}

  listarDetalles(): Observable<CompraDetails[]> {
    return this.http.get<CompraDetails[]>(this.apiUrl);
  }

  obtenerDetalle(id: number): Observable<CompraDetails> {
    return this.http.get<CompraDetails>(`${this.apiUrl}/${id}`);
  }

  crearDetalle(form: CompraDetailForm): Observable<CompraDetails> {
    return this.http.post<CompraDetails>(this.apiUrl, form);
  }

  actualizarDetalle(form: CompraDetailForm): Observable<CompraDetails> {
    return this.http.put<CompraDetails>(this.apiUrl, form);
  }

  eliminarDetalle(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Método útil para obtener detalles por compra (si el backend lo soporta)
  // Si no existe endpoint, se puede filtrar en frontend
  listarDetallesPorCompra(compraId: number): Observable<CompraDetails[]> {
    return this.http.get<CompraDetails[]>(`${this.apiUrl}?compraId=${compraId}`);
  }
}
