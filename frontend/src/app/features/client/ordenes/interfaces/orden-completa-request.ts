import { OrdenDetailsRequest } from './orden-details-request';
import { OrdenRequest } from './orden-request';

export interface OrdenCompletaRequest {
  orden: OrdenRequest;
  detalles: OrdenDetailsRequest[];
}
