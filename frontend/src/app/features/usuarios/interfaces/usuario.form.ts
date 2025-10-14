export interface UsuarioForm {
  id: number;
  nombre: string;
  email: string;
  estado: string;
  passwordHash: string;
  rolId: number;
}
