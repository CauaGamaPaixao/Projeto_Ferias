import { TestBed } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { OrderReceiptComponent } from './order-receipt.component';
import { receiptOrder } from '../../testing/order.fixture';

describe('OrderReceiptComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [CommonModule], declarations: [OrderReceiptComponent]
  }));

  it('renders item snapshots, exact totals, buyer, date, installments and non-fiscal notice', () => {
    const fixture = TestBed.createComponent(OrderReceiptComponent);
    fixture.componentInstance.order = receiptOrder;
    fixture.detectChanges();
    const root: HTMLElement = fixture.nativeElement;
    expect(root.querySelectorAll('tbody tr').length).toBe(2);
    expect(root.textContent).toContain('Camisa Brasil');
    expect(root.textContent).toContain('719,70');
    expect(root.textContent).toContain('299,90');
    expect(root.textContent).toContain('Ana Silva');
    expect(root.textContent).toContain('14/09/2026');
    expect(root.textContent).toContain('09:00');
    expect(root.textContent).toContain('3 parcelas sem juros');
    expect(root.textContent).toContain('Apto 42');
    expect(root.textContent).toContain('Documento não fiscal');
    expect(root.textContent).toContain('compra simulada');
  });

  it('supports a single item, PIX and no address complement', () => {
    const fixture = TestBed.createComponent(OrderReceiptComponent);
    fixture.componentInstance.order = { ...receiptOrder, items: [receiptOrder.items[1]],
      total: 119.90, paymentMethod: 'PIX', installments: 1,
      address: { ...receiptOrder.address, complemento: '' } };
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelectorAll('tbody tr').length).toBe(1);
    expect(fixture.nativeElement.textContent).toContain('À vista');
    expect(fixture.nativeElement.textContent).not.toContain('Apto 42');
    expect(fixture.nativeElement.textContent).not.toContain('undefined');
  });
});
