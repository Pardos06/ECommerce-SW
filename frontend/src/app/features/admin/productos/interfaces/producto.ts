export interface Producto {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  disponibilidad: string;
  categoriaId?: number;  
  categoria: string;
  imagenNombre: string;
}
