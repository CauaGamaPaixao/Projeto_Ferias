import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class WishlistService {
  private readonly baseUrl = '/api/wishlist';

  constructor(private http: HttpClient) {}

  getItems(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl);
  }

  toggle(productId: number): Observable<{ wishlistIds: number[] }> {
    return this.http.post<{ wishlistIds: number[] }>(`${this.baseUrl}/toggle`, { productId });
  }
}
