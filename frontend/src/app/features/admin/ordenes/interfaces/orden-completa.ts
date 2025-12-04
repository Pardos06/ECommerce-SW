import { Orden } from "./orden";
import { OrdenDetails } from "./orden-details";

export interface OrdenCompleta {
  orden: Orden;
  detalles: OrdenDetails[];
}
