import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({ selector: 'app-auth', templateUrl: './auth.component.html' })
export class AuthComponent {
  mode: 'login' | 'register' = 'login';
  email = ''; password = ''; name = '';
  error = '';
  private returnUrl = '/';

  constructor(private auth: AuthService, private router: Router, route: ActivatedRoute) {
    this.mode = route.snapshot.data['mode'] || 'login';
    const requested = route.snapshot.queryParamMap.get('returnUrl') || '/';
    if (/^\/pedidos(?:\/[A-Za-z0-9-]+\/confirmacao)?$/.test(requested) || requested === '/checkout') {
      this.returnUrl = requested;
    }
  }

  login(): void {
    this.auth.login(this.email, this.password).subscribe({
      next: () => this.router.navigateByUrl(this.returnUrl),
      error: () => this.error = 'E-mail ou senha incorretos.'
    });
  }

  register(): void {
    this.auth.register(this.name, this.email, this.password).subscribe({
      next: () => this.router.navigateByUrl(this.returnUrl),
      error: () => this.error = 'Erro ao criar conta. Verifique os dados.'
    });
  }
}
