import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CartService } from './cart.service';
import { WishlistService } from './wishlist.service';

@Injectable({ providedIn: 'root' })
export class ShoppingStateService {
  private cartCount$ = new BehaviorSubject<number>(0);
  private wishlistCount$ = new BehaviorSubject<number>(0);
  private wishlistIds$ = new BehaviorSubject<number[]>([]);

  cartCount = this.cartCount$.asObservable();
  wishlistCount = this.wishlistCount$.asObservable();
  wishlistIds = this.wishlistIds$.asObservable();

  constructor(private cartService: CartService, private wishlistService: WishlistService) {}

  refresh(): void {
    this.cartService.getItems().subscribe(data => {
      this.cartCount$.next(data.items.reduce((s, i) => s + i.quantity, 0));
    });
    this.wishlistService.getItems().subscribe(items => {
      this.wishlistCount$.next(items.length);
      this.wishlistIds$.next(items.map(p => p.id));
    });
  }

  addToCart(productId: number): void {
    this.cartService.add(productId).subscribe(() => this.refresh());
  }

  toggleWishlist(productId: number): void {
    this.wishlistService.toggle(productId).subscribe(res => {
      this.wishlistIds$.next(res.wishlistIds);
      this.wishlistCount$.next(res.wishlistIds.length);
    });
  }
}
