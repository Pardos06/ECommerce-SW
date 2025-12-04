import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartItem } from '../interfaces/cart-item';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private key = 'cart_items';
  private itemsSubject = new BehaviorSubject<CartItem[]>(this.loadCart());
  items$ = this.itemsSubject.asObservable();

  private loadCart(): CartItem[] {
    return JSON.parse(localStorage.getItem(this.key) || '[]');
  }

  private saveCart(items: CartItem[]) {
    localStorage.setItem(this.key, JSON.stringify(items));
  }

  private mapToCartItem(product: any): CartItem {
    return {
      idProducto: product.idProducto ?? product.id,
      nombre: product.nombre,
      precio: product.precio,
      imagen: product.imagen ?? product.imagenNombre ?? '',
      cantidad: 1
    };
  }

  addItem(product: any, cantidad: number = 1) {
    const cart = this.loadCart();

    const converted = this.mapToCartItem(product);

    const existente = cart.find(i => i.idProducto === converted.idProducto);

    if (existente) {
      existente.cantidad += cantidad;
    } else {
      converted.cantidad = cantidad;
      cart.push(converted);
    }

    this.saveCart(cart);
    this.itemsSubject.next(cart);
  }

  updateCantidad(idProducto: number, cantidad: number) {
    const cart = this.loadCart();
    const item = cart.find(i => i.idProducto === idProducto);
    if (item) item.cantidad = cantidad;

    this.saveCart(cart);
    this.itemsSubject.next(cart);
  }

  removeItem(idProducto: number) {
    const nuevo = this.loadCart().filter(i => i.idProducto !== idProducto);
    this.saveCart(nuevo);
    this.itemsSubject.next(nuevo);
  }

  clear() {
    this.saveCart([]);
    this.itemsSubject.next([]);
  }

  getItems(): CartItem[] {
    return this.loadCart();
  }

  getTotal(): number {
    return this.loadCart().reduce((acc, i) => acc + i.precio * i.cantidad, 0);
  }
}
