package br.com.projetoferias.service;

import br.com.projetoferias.model.CheckoutAddress;
import br.com.projetoferias.model.OrderSummary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    public OrderSummary createOrder(ShoppingSessionService shoppingSession,
                                    ProductService productService,
                                    CheckoutAddress address,
                                    String paymentMethod) {
        return new OrderSummary(
                "BM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                shoppingSession.cartItems(productService),
                shoppingSession.total(productService),
                address,
                paymentMethod,
                LocalDateTime.now()
        );
    }
}
