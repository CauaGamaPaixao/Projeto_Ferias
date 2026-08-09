import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { ShoppingStateService } from '../../services/shopping-state.service';
import { AuthService } from '../../services/auth.service';
import { Product } from '../../models/product.model';

@Component({ selector: 'app-home', templateUrl: './home.component.html' })
export class HomeComponent implements OnInit {
  products: Product[] = [];
  categories: string[] = [];
  selectedCategory = '';
  query = '';
  wishlistIds: number[] = [];

  constructor(
    private productService: ProductService,
    public state: ShoppingStateService,
    public auth: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.query = params['q'] || '';
      this.selectedCategory = params['category'] || '';
      this.productService.getAll(this.selectedCategory, this.query)
        .subscribe(p => this.products = p);
    });
    this.productService.getCategories().subscribe(c => this.categories = c);
    this.state.wishlistIds.subscribe(ids => this.wishlistIds = ids);
  }

  get userInitial(): string {
    const name = this.auth.currentUser?.name || '';
    return name.charAt(0).toUpperCase();
  }

  get userName(): string {
    return this.auth.currentUser?.name || '';
  }

  applyFilters(): void {
    this.router.navigate(['/'], {
      queryParams: {
        ...(this.query ? { q: this.query } : {}),
        ...(this.selectedCategory ? { category: this.selectedCategory } : {})
      }
    });
  }

  clearFilters(): void {
    this.query = '';
    this.selectedCategory = '';
    this.router.navigate(['/']);
  }

  addToCart(productId: number): void {
    this.state.addToCart(productId);
  }

  toggleFavorite(productId: number): void {
    this.state.toggleWishlist(productId);
  }

  isFavorite(productId: number): boolean {
    return this.wishlistIds.includes(productId);
  }

  formatPrice(price: number): string {
    return price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }
}