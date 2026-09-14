import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { CopaAssistantComponent } from './components/copa-assistant/copa-assistant.component';
import { HomeComponent } from './pages/home/home.component';
import { CartComponent } from './pages/cart/cart.component';
import { WishlistComponent } from './pages/wishlist/wishlist.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { AuthComponent } from './pages/auth/auth.component';
import { OrderSuccessComponent } from './pages/order-success/order-success.component';
import { OrdersComponent } from './pages/orders/orders.component';
import { OrderReceiptComponent } from './components/order-receipt/order-receipt.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    CopaAssistantComponent,
    HomeComponent,
    CartComponent,
    WishlistComponent,
    CheckoutComponent,
    AuthComponent,
    OrderSuccessComponent,
    OrdersComponent,
    OrderReceiptComponent,
  ],
  imports: [BrowserModule, HttpClientModule, FormsModule, AppRoutingModule],
  bootstrap: [AppComponent]
})
export class AppModule {}
