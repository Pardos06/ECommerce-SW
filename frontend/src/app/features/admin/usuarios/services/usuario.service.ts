import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environment/environment';
import { Usuario } from '../interfaces/usuario';
import { UsuarioCreateForm } from '../interfaces/usuario-create-form';
import { UsuarioForm } from '../interfaces/usuario-form';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = environment.apiUrl;

  // Listar todos los usuarios
  listarUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios`);
  }

  // Obtener usuario por ID
  obtenerUsuarioPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/usuarios/${id}`);
  }

  // Crear usuario (nuevo)
  crearUsuario(usuario: UsuarioCreateForm): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/usuarios`, usuario);
  }

  // Actualizar usuario existente
  actualizarUsuario(usuario: UsuarioForm): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/usuarios`, usuario);
  }

  // Eliminar usuario
  eliminarUsuario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/usuarios/${id}`);
  }
}
