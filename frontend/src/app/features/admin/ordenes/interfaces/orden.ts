import { OrdenDetails } from "./orden-details";

export interface Orden {
  id: number;
  estado: string;
  estadoEmail: string;
  cliente: string;
  metodoPago: string;
  clienteId: number;
  metodoPagoId: number;
  details: OrdenDetails[];
  fechaOrden: string;
}