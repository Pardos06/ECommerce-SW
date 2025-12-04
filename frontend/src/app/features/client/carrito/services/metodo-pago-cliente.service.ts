import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { MetodoPago } from '../interfaces/metodo-pago';

@Injectable({
  providedIn: 'root'
})
export class MetodoPagoClienteService {

  private apiUrl = environment.apiUrl + '/metodos-pago';

  constructor(private http: HttpClient) {}

  listar(): Observable<MetodoPago[]> {
    return this.http.get<MetodoPago[]>(this.apiUrl);
  }
}
