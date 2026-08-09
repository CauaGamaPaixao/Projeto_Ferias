import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { CartItem } from '../../models/product.model';

@Component({ selector: 'app-cart', templateUrl: './cart.component.html' })
export class CartComponent implements OnInit {
  items: CartItem[] = [];
  total = 0;

  constructor(private cartService: CartService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.cartService.getItems().subscribe(data => { this.items = data.items; this.total = data.total; });
  }

  remove(productId: number): void {
    this.cartService.remove(productId).subscribe(() => this.load());
  }

  formatPrice(price: number): string {
    return price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }
}
