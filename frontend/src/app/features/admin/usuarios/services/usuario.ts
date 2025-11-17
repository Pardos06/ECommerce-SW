import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { Usuario } from '../interfaces/usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  /**
   * Listar todos los usuarios
   */
  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios`);
  }

  /**
   * Obtener un usuario por ID
   */
  obtenerUsuarioPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/usuarios/${id}`);
  }

  /**
   * Obtener usuario por email
   */
  obtenerUsuarioPorEmail(email: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/usuarios/email/${email}`);
  }

  /**
   * Crear un nuevo usuario
   */
  crearUsuario(usuario: Partial<Usuario>): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/usuarios`, usuario);
  }

  /**
   * Actualizar un usuario existente
   */
  actualizarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/usuarios/${usuario.id}`, usuario);
  }

  /**
   * Cambiar el estado de un usuario (activo/inactivo)
   */
  cambiarEstadoUsuario(id: number, estado: string): Observable<Usuario> {
    return this.http.patch<Usuario>(`${this.apiUrl}/usuarios/${id}/estado`, { estado });
  }

  /**
   * Cambiar el rol de un usuario
   */
  cambiarRolUsuario(id: number, rol: string): Observable<Usuario> {
    return this.http.patch<Usuario>(`${this.apiUrl}/usuarios/${id}/rol`, { rol });
  }

  /**
   * Eliminar un usuario
   */
  eliminarUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/usuarios/${id}`);
  }

  /**
   * Listar usuarios por rol
   */
  listarUsuariosPorRol(rol: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios/rol/${rol}`);
  }

  /**
   * Listar usuarios por estado
   */
  listarUsuariosPorEstado(estado: string): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios/estado/${estado}`);
  }
}
