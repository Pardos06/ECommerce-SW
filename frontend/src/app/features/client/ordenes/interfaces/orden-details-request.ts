export interface OrdenDetailsRequest {
  id?: number;
  cantidad: number;
  precioUnitario: number;
  ordenId?: number;
  productoId: number;
}
