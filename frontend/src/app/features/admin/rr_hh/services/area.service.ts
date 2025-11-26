import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Area } from '../interfaces/area';
import { environment } from '../../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class AreaService {
  private apiUrl = environment.apiUrl + '/areas';

  constructor(private http: HttpClient) {}

  listarAreas(): Observable<Area[]> {
    return this.http.get<Area[]>(this.apiUrl);
  }

  obtenerArea(id: number): Observable<Area> {
    return this.http.get<Area>(`${this.apiUrl}/${id}`);
  }

  registrarArea(area: Area): Observable<Area> {
    return this.http.post<Area>(this.apiUrl, area);
  }

  editarArea(area: Area): Observable<Area> {
    return this.http.put<Area>(this.apiUrl, area);
  }

  eliminarArea(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
