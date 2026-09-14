import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subject, catchError, of, startWith, switchMap, takeUntil, tap } from 'rxjs';
import { Order } from '../../models/product.model';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-order-success',
  templateUrl: './order-success.component.html',
  styleUrls: ['./order-success.component.css']
})
export class OrderSuccessComponent implements OnInit, OnDestroy {
  order: Order | null = null;
  loading = true;
  error = '';
  unauthorized = false;
  returnUrl = '/pedidos';
  private destroy$ = new Subject<void>();
  private reload$ = new Subject<void>();

  constructor(private route: ActivatedRoute, private orders: OrderService) {}

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap(params => this.reload$.pipe(
        startWith(undefined),
        tap(() => {
          this.order = null;
          this.loading = true;
          this.error = '';
          this.unauthorized = false;
          this.returnUrl = '/pedidos/' + params.get('code') + '/confirmacao';
        }),
        switchMap(() => this.orders.get(params.get('code') || '').pipe(
          catchError(error => {
            this.unauthorized = error.status === 401;
            this.error = error.status === 401 ? 'Entre na sua conta para consultar este comprovante.'
              : error.status === 403 ? 'Este pedido pertence a outra conta.'
              : error.status === 404 ? 'Pedido não encontrado. Confira o código ou consulte Meus pedidos.'
              : 'Não foi possível carregar o comprovante. Tente novamente.';
            return of(null);
          })
        ))
      )),
      takeUntil(this.destroy$)
    ).subscribe(order => { this.order = order; this.loading = false; });
  }

  print(): void {
    if (this.order && !this.loading) window.print();
  }

  retry(): void {
    this.reload$.next();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
