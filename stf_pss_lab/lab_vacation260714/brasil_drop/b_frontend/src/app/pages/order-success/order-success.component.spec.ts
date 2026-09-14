import { TestBed } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { BehaviorSubject } from 'rxjs';
import { OrderSuccessComponent } from './order-success.component';
import { OrderReceiptComponent } from '../../components/order-receipt/order-receipt.component';
import { receiptOrder } from '../../testing/order.fixture';

describe('OrderSuccessComponent', () => {
  let http: HttpTestingController;
  const params = new BehaviorSubject(convertToParamMap({ code: 'BD-TEST123' }));

  beforeEach(() => {
    params.next(convertToParamMap({ code: 'BD-TEST123' }));
    TestBed.configureTestingModule({
      imports: [CommonModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [OrderSuccessComponent, OrderReceiptComponent],
      providers: [{ provide: ActivatedRoute, useValue: { paramMap: params } }]
    });
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('loads a direct URL without navigation state and prints only after loading', () => {
    const fixture = TestBed.createComponent(OrderSuccessComponent);
    const print = spyOn(window, 'print');
    fixture.detectChanges();
    fixture.componentInstance.print();
    expect(print).not.toHaveBeenCalled();
    expect(fixture.nativeElement.textContent).toContain('Carregando');
    http.expectOne('/api/orders/BD-TEST123').flush(receiptOrder);
    fixture.detectChanges();
    const button: HTMLButtonElement = fixture.nativeElement.querySelector('.receipt-actions button');
    button.click();
    expect(print).toHaveBeenCalledTimes(1);
    expect(fixture.nativeElement.textContent).toContain('719,70');
  });

  for (const [status, message] of [[401, 'Entre na sua conta'], [403, 'outra conta'], [404, 'Pedido não encontrado'], [500, 'Tente novamente']] as const) {
    it('shows a useful error for HTTP ' + status, () => {
      const fixture = TestBed.createComponent(OrderSuccessComponent);
      fixture.detectChanges();
      http.expectOne('/api/orders/BD-TEST123').flush({}, { status, statusText: 'Error' });
      fixture.detectChanges();
      expect(fixture.nativeElement.textContent).toContain(message);
      expect(fixture.nativeElement.querySelector('app-order-receipt')).toBeNull();
    });
  }

  it('can retry a failed request and reload when the route code changes', () => {
    const fixture = TestBed.createComponent(OrderSuccessComponent);
    fixture.detectChanges();
    http.expectOne('/api/orders/BD-TEST123').flush({}, { status: 500, statusText: 'Error' });
    fixture.componentInstance.retry();
    http.expectOne('/api/orders/BD-TEST123').flush(receiptOrder);
    params.next(convertToParamMap({ code: 'BD-SECOND' }));
    expect(fixture.componentInstance.order).toBeNull();
    http.expectOne('/api/orders/BD-SECOND').flush({ ...receiptOrder, code: 'BD-SECOND' });
    expect(fixture.componentInstance.order?.code).toBe('BD-SECOND');
  });
});
