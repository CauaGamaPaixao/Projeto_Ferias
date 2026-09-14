import { Component, Input } from '@angular/core';
import { Order } from '../../models/product.model';

@Component({
  selector: 'app-order-receipt',
  templateUrl: './order-receipt.component.html',
  styleUrls: ['./order-receipt.component.css']
})
export class OrderReceiptComponent {
  @Input({ required: true }) order!: Order;

  get paymentLabel(): string {
    return ({ PIX: 'PIX', CARTAO_CREDITO: 'Cartão de crédito', BOLETO: 'Boleto' } as Record<string, string>)
      [this.order.paymentMethod] || this.order.paymentMethod;
  }

  money(value: number): string {
    return value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  get dateLabel(): string {
    return new Intl.DateTimeFormat('pt-BR', {
      dateStyle: 'short', timeStyle: 'short', timeZone: 'America/Sao_Paulo'
    }).format(new Date(this.order.createdAt));
  }
}
