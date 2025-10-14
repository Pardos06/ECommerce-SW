export interface Orden {
  id: number;
  fechaOrden: string;
  estado: string;
  estadoEmail: string;
  cliente: string;
  metodoPago: string;
  clienteId: number;
  metodoPagoId: number;
}
