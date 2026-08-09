import { Component, OnInit, HostListener } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ShoppingStateService } from '../../services/shopping-state.service';
import { FontSizeService } from '../../services/font-size.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
})
export class NavbarComponent implements OnInit {
  cartCount = 0;
  wishlistCount = 0;
  query = '';
  userMenuOpen = false;

  constructor(
    public auth: AuthService,
    public state: ShoppingStateService,
    public fontSize: FontSizeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.state.refresh();
    this.state.cartCount.subscribe(c => this.cartCount = c);
    this.state.wishlistCount.subscribe(c => this.wishlistCount = c);
  }

  get userInitial(): string {
    return this.auth.currentUser?.name?.charAt(0).toUpperCase() || '';
  }

  get userName(): string {
    return this.auth.currentUser?.name || '';
  }

  toggleUserMenu(): void {
    this.userMenuOpen = !this.userMenuOpen;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.user-menu-wrapper')) {
      this.userMenuOpen = false;
    }
  }

  search(): void {
    this.router.navigate(['/'], { queryParams: this.query ? { q: this.query } : {} });
  }

  logout(): void {
    this.userMenuOpen = false;
    this.auth.logout().subscribe(() => this.router.navigate(['/login']));
  }
}