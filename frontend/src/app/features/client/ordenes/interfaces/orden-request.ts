export interface OrdenRequest {
  id?: number;
  fechaOrden?: string;
  estado: string;
  estadoEmail: string;
  clienteId: number;
  metodoPagoId: number;
}