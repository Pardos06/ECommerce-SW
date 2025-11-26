import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Rol } from '../interfaces/rol';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class RolService {
  private apiUrl = environment.apiUrl + '/roles';

  constructor(private http: HttpClient) { }

  listarRoles(): Observable<Rol[]> {
    return this.http.get<Rol[]>(this.apiUrl);
  }

  obtenerRol(id: number): Observable<Rol> {
    return this.http.get<Rol>(`${this.apiUrl}/${id}`);
  }

  registrarRol(rol: Rol): Observable<Rol> {
    return this.http.post<Rol>(this.apiUrl, rol);
  }

  editarRol(rol: Rol): Observable<Rol> {
    return this.http.put<Rol>(this.apiUrl, rol);
  }

  eliminarRol(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  buscarPorNombre(nombre: string): Observable<Rol[]> {
    return this.http.get<Rol[]>(`${this.apiUrl}/search/${nombre}`);
  }
}
