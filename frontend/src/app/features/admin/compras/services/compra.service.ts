import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Compra } from '../interfaces/compra';
import { CompraForm } from '../interfaces/compra.form';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private readonly apiUrl = environment.apiUrl + '/compras';

  constructor(private readonly http: HttpClient) {}

  listarCompras(): Observable<Compra[]> {
    return this.http.get<Compra[]>(this.apiUrl);
  }

  obtenerCompra(id: number): Observable<Compra> {
    return this.http.get<Compra>(`${this.apiUrl}/${id}`);
  }

  crearCompra(form: CompraForm): Observable<Compra> {
    return this.http.post<Compra>(this.apiUrl, form);
  }

  actualizarCompra(form: CompraForm): Observable<Compra> {
    return this.http.put<Compra>(this.apiUrl, form);
  }

  eliminarCompra(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
