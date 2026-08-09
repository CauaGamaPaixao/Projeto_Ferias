import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Order } from '../../models/product.model';

@Component({ selector: 'app-order-success', templateUrl: './order-success.component.html' })
export class OrderSuccessComponent {
  order: Order | null = null;

  constructor(private router: Router) {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras?.state?.['order'];
    if (state) {
      this.order = state;
    } else {
      this.router.navigate(['/']);
    }
  }

  get paymentLabel(): string {
    switch (this.order?.paymentMethod) {
      case 'CARTAO_CREDITO': return 'Cartao de credito';
      case 'BOLETO': return 'Boleto';
      case 'PIX': return 'PIX';
      default: return this.order?.paymentMethod || '';
    }
  }

  get isInstallment(): boolean {
    return (this.order?.installments ?? 1) > 1;
  }

  get installmentValue(): number {
    if (!this.order) return 0;
    return this.order.total / (this.order.installments || 1);
  }

  formatPrice(price: number): string {
    return price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }
}