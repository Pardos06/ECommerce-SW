import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MetodoPago } from '../interfaces/metodo.pago';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class MetodoPagoService {
  private apiUrl = environment.apiUrl + '/metodos-pago';

  constructor(private http: HttpClient) {}

  listar(): Observable<MetodoPago[]> {
    return this.http.get<MetodoPago[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<MetodoPago> {
    return this.http.get<MetodoPago>(`${this.apiUrl}/${id}`);
  }

  registrar(metodo: MetodoPago): Observable<MetodoPago> {
    return this.http.post<MetodoPago>(this.apiUrl, metodo);
  }

  editar(metodo: MetodoPago): Observable<MetodoPago> {
    return this.http.put<MetodoPago>(this.apiUrl, metodo);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
