import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cargo } from '../interfaces/cargo';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CargoService {
  private apiUrl = environment.apiUrl + '/cargos';

  constructor(private http: HttpClient) {}

  listarCargos(): Observable<Cargo[]> {
    return this.http.get<Cargo[]>(this.apiUrl);
  }

  obtenerCargo(id: number): Observable<Cargo> {
    return this.http.get<Cargo>(`${this.apiUrl}/${id}`);
  }

  registrarCargo(cargo: Cargo): Observable<Cargo> {
    return this.http.post<Cargo>(this.apiUrl, cargo);
  }

  editarCargo(cargo: Cargo): Observable<Cargo> {
    return this.http.put<Cargo>(this.apiUrl, cargo);
  }

  eliminarCargo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
