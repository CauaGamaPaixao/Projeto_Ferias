package br.com.projetoferias.controller;

import br.com.projetoferias.controller.dto.CheckoutRequest;
import br.com.projetoferias.controller.dto.OrderResponse;
import br.com.projetoferias.model.*;
import br.com.projetoferias.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final ProductService productService;
    private final ShoppingSessionService shoppingSession;
    private final ViaCepService viaCepService;
    private final OrderService orderService;

    public CheckoutController(ProductService productService, ShoppingSessionService shoppingSession,
                              ViaCepService viaCepService, OrderService orderService) {
        this.productService = productService;
        this.shoppingSession = shoppingSession;
        this.viaCepService = viaCepService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> pay(@Valid @RequestBody CheckoutRequest body, HttpSession session) {
        synchronized (session) {
            UserAccount user = (UserAccount) session.getAttribute("user");
            if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            if (shoppingSession.cartCount() == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            CheckoutAddress address = viaCepService.findAddress(body.cep())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            CheckoutAddress fullAddress = new CheckoutAddress(address.cep(), body.rua().trim(), address.bairro(),
                    address.localidade(), address.uf(), body.numero().trim(),
                    body.complemento() == null ? "" : body.complemento().trim());
            OrderSummary order = orderService.createOrder(user, shoppingSession.cartItems(productService),
                    fullAddress, body.paymentMethod(), body.installments());
            shoppingSession.clearCart();
            return ResponseEntity.ok().header("Cache-Control", "no-store").body(OrderResponse.from(order));
        }
    }
}
