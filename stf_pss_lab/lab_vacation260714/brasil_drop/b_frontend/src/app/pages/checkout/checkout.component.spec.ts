import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { CheckoutComponent } from './checkout.component';
import { CartService } from '../../services/cart.service';
import { ShoppingStateService } from '../../services/shopping-state.service';
import { receiptOrder } from '../../testing/order.fixture';

describe('CheckoutComponent', () => {
  let http: HttpTestingController;
  let component: CheckoutComponent;
  let router: jasmine.SpyObj<Router>;
  let state: { refresh: jasmine.Spy };
  beforeEach(() => {
    router = jasmine.createSpyObj('Router', ['navigate']);
    state = { refresh: jasmine.createSpy('refresh') };
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CheckoutComponent,
        { provide: Router, useValue: router },
        { provide: ShoppingStateService, useValue: state },
        { provide: CartService, useValue: { getItems: () => of({ items: [], total: 0 }) } }]
    });
    component = TestBed.inject(CheckoutComponent);
    http = TestBed.inject(HttpTestingController);
    component.cepValido = true;
    component.cep = '01001-000';
    component.numero = '123';
  });
  afterEach(() => http.verify());

  it('navigates using the persisted code and prevents double submission', () => {
    component.submit();
    component.submit();
    http.expectOne('/api/checkout').flush(receiptOrder);
    expect(router.navigate).toHaveBeenCalledWith(['/pedidos', receiptOrder.code, 'confirmacao']);
    expect(state.refresh).toHaveBeenCalledTimes(1);
  });

  it('keeps the form usable after failure and offers order reconciliation', () => {
    component.submit();
    http.expectOne('/api/checkout').flush({}, { status: 500, statusText: 'Error' });
    expect(component.submitting).toBeFalse();
    expect(component.error).toContain('Meus pedidos');
    expect(router.navigate).not.toHaveBeenCalled();
    expect(state.refresh).not.toHaveBeenCalled();
  });

  it('rejects missing address fields without sending checkout', () => {
    component.numero = '';
    component.submit();
    http.expectNone('/api/checkout');
    expect(component.error).toContain('número');
  });
});
