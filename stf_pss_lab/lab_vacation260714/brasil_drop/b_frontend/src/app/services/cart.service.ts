import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CartItem } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly baseUrl = '/api/cart';

  constructor(private http: HttpClient) {}

  getItems(): Observable<{ items: CartItem[]; total: number }> {
    return this.http.get<{ items: CartItem[]; total: number }>(this.baseUrl);
  }

  add(productId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/add`, { productId });
  }

  remove(productId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/remove`, { productId });
  }
}
