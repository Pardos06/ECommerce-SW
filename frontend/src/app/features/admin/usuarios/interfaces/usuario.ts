export interface Usuario {
  id: number;
  nombre: string;
  email: string;
  estado: string;
  rol: string;
  clienteId?: number;
  empleadoId?: number;
}
