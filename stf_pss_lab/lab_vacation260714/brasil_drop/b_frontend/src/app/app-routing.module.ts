import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { CartComponent } from './pages/cart/cart.component';
import { WishlistComponent } from './pages/wishlist/wishlist.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { AuthComponent } from './pages/auth/auth.component';
import { OrderSuccessComponent } from './pages/order-success/order-success.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { authGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'cart', component: CartComponent },
  { path: 'wishlist', component: WishlistComponent },
  { path: 'checkout', component: CheckoutComponent, canActivate: [authGuard] },
  { path: 'login', component: AuthComponent, data: { mode: 'login' } },
  { path: 'cadastro', component: AuthComponent, data: { mode: 'register' } },
  { path: 'pedidos', component: OrdersComponent },
  { path: 'pedidos/:code/confirmacao', component: OrderSuccessComponent },
  { path: 'pedido-confirmado', redirectTo: 'pedidos', pathMatch: 'full' },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
