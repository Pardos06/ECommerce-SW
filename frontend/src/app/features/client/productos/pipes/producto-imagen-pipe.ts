import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'productoImagen'
})
export class ProductoImagenPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
