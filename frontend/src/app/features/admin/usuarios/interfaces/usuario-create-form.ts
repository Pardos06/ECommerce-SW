export interface UsuarioCreateForm {
  nombre: string;
  email: string;
  passwordHash: string; // plain text que será encriptado en el backend
  estado: string;
  rolId: number;
}