import { Component, OnInit } from '@angular/core';
import { WishlistService } from '../../services/wishlist.service';
import { CartService } from '../../services/cart.service';
import { Product } from '../../models/product.model';

@Component({ selector: 'app-wishlist', templateUrl: './wishlist.component.html' })
export class WishlistComponent implements OnInit {
  products: Product[] = [];

  constructor(private wishlistService: WishlistService, private cartService: CartService) {}

  ngOnInit(): void {
    this.wishlistService.getItems().subscribe(p => this.products = p);
  }

  addToCart(productId: number): void { this.cartService.add(productId).subscribe(); }

  formatPrice(price: number): string {
    return price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }
}
