export interface UsuarioCreateForm {
  nombre: string;
  email: string;
  password: string; // plain text
  estado: string;
  rolId: number;
}