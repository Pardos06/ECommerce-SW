import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empleado } from '../interfaces/empleado';
import { EmpleadoForm } from '../interfaces/empleado-form';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  private readonly apiUrl = environment.apiUrl + '/empleados';

  constructor(private readonly http: HttpClient) {}

  listarEmpleados(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(this.apiUrl);
  }

  obtenerEmpleado(id: number): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.apiUrl}/${id}`);
  }

  registrarEmpleado(form: EmpleadoForm): Observable<Empleado> {
    return this.http.post<Empleado>(this.apiUrl, form);
  }

  actualizarEmpleado(form: EmpleadoForm): Observable<Empleado> {
    return this.http.put<Empleado>(this.apiUrl, form);
  }

  eliminarEmpleado(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
