import { TestBed } from '@angular/core/testing';
import { CommonModule } from '@angular/common';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { OrdersComponent } from './orders.component';
import { receiptOrder } from '../../testing/order.fixture';

describe('OrdersComponent', () => {
  let http: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CommonModule, RouterTestingModule, HttpClientTestingModule],
      declarations: [OrdersComponent]
    });
    http = TestBed.inject(HttpTestingController);
  });
  afterEach(() => http.verify());

  it('shows an empty history then a link to the saved receipt on reload', () => {
    const fixture = TestBed.createComponent(OrdersComponent);
    fixture.detectChanges();
    http.expectOne('/api/orders').flush([]);
    fixture.detectChanges();
    expect(fixture.nativeElement.textContent).toContain('Você ainda não tem pedidos');
    fixture.componentInstance.load();
    http.expectOne('/api/orders').flush([receiptOrder]);
    fixture.detectChanges();
    expect(fixture.nativeElement.querySelector('ol a').getAttribute('href')).toBe('/pedidos/BD-TEST123/confirmacao');
  });

  it('offers login for an expired session without showing stale orders', () => {
    const fixture = TestBed.createComponent(OrdersComponent);
    fixture.detectChanges();
    http.expectOne('/api/orders').flush([receiptOrder]);
    fixture.componentInstance.load();
    http.expectOne('/api/orders').flush({}, { status: 401, statusText: 'Unauthorized' });
    fixture.detectChanges();
    expect(fixture.nativeElement.textContent).toContain('Entre na sua conta');
    expect(fixture.nativeElement.querySelector('ol')).toBeNull();
  });
});
