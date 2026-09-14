import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Order } from '../../models/product.model';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit, OnDestroy {
  orders: Order[] = [];
  loading = true;
  error = '';
  unauthorized = false;
  private request?: Subscription;

  constructor(private service: OrderService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.request?.unsubscribe();
    this.orders = [];
    this.loading = true;
    this.error = '';
    this.unauthorized = false;
    this.request = this.service.list().subscribe({
      next: orders => { this.orders = orders; this.loading = false; },
      error: error => {
        this.loading = false;
        this.unauthorized = error.status === 401;
        this.error = this.unauthorized ? 'Entre na sua conta para ver seus pedidos.'
          : 'Não foi possível carregar seus pedidos. Tente novamente.';
      }
    });
  }

  date(value: string): string {
    return new Intl.DateTimeFormat('pt-BR', { dateStyle: 'short', timeZone: 'America/Sao_Paulo' }).format(new Date(value));
  }

  money(value: number): string {
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  ngOnDestroy(): void { this.request?.unsubscribe(); }
}
