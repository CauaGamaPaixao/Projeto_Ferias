import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  constructor(private http: HttpClient) {}

  get(code: string): Observable<Order> {
    return this.http.get<Order>(`/api/orders/${encodeURIComponent(code)}`);
  }

  list(): Observable<Order[]> {
    return this.http.get<Order[]>('/api/orders');
  }
}
