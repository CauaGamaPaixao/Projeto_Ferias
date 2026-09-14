import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { ViacepService } from '../../services/viacep.service';
import { ShoppingStateService } from '../../services/shopping-state.service';
import { HttpClient } from '@angular/common/http';
import { CartItem, Order } from '../../models/product.model';

@Component({ selector: 'app-checkout', templateUrl: './checkout.component.html' })
export class CheckoutComponent implements OnInit {
  items: CartItem[] = [];
  total = 0;
  cep = '';
  rua = '';
  numero = '';
  complemento = '';
  bairro = '';
  cidade = '';
  uf = '';
  cepValido = false;
  paymentMethod = 'PIX';
  installments = 1;
  error = '';
  submitting = false;

  constructor(
    private cartService: CartService,
    private viacepService: ViacepService,
    private state: ShoppingStateService,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.getItems().subscribe(data => {
      this.items = data.items;
      this.total = data.total;
    });
  }

  get maxInstallments(): number {
    if (this.paymentMethod === 'CARTAO_CREDITO') return 10;
    if (this.paymentMethod === 'BOLETO') return 6;
    return 1;
  }

  get installmentOptions(): number[] {
    return Array.from({ length: this.maxInstallments }, (_, i) => i + 1);
  }

  get installmentValue(): number {
    return this.total / this.installments;
  }

  get showInstallments(): boolean {
    return this.paymentMethod === 'CARTAO_CREDITO' || this.paymentMethod === 'BOLETO';
  }

  onPaymentMethodChange(): void {
    this.installments = 1;
  }

  lookupCep(): void {
    const clean = this.cep.replace(/\D/g, '');
    if (clean.length !== 8) return;
    this.error = '';
    this.viacepService.lookup(this.cep).subscribe({
      next: addr => {
        if (addr.erro) {
          this.cepValido = false;
          this.error = 'CEP não encontrado.';
          this.clearAddress();
          return;
        }
        this.cepValido = true;
        this.rua = addr.logradouro || '';
        this.bairro = addr.bairro || '';
        this.cidade = addr.localidade || '';
        this.uf = addr.uf || '';
      },
      error: () => { this.error = 'Erro ao buscar CEP.'; this.cepValido = false; }
    });
  }

  private clearAddress(): void {
    this.rua = ''; this.bairro = ''; this.cidade = ''; this.uf = '';
  }

  submit(): void {
    if (this.submitting) return;
    if (!this.cepValido) { this.error = 'Informe um CEP válido.'; return; }
    if (!this.numero.trim()) { this.error = 'Informe o número do endereço.'; return; }
    this.submitting = true;
    this.error = '';
    this.http.post<Order>('/api/checkout', {
      cep: this.cep,
      rua: this.rua,
      numero: this.numero,
      complemento: this.complemento,
      paymentMethod: this.paymentMethod,
      installments: this.installments
    }).subscribe({
      next: order => {
        this.state.refresh();
        this.router.navigate(['/pedidos', order.code, 'confirmacao']);
      },
      error: error => {
        this.submitting = false;
        this.error = error.status === 401 ? 'Sua sessão expirou. Entre novamente para finalizar.'
          : 'Não foi possível confirmar a compra. Consulte Meus pedidos antes de tentar novamente.';
      }
    });
  }

  formatPrice(price: number): string {
    return price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }
}
