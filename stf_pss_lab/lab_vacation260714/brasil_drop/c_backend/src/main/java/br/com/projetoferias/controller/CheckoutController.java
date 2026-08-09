package br.com.projetoferias.controller;

import br.com.projetoferias.model.CheckoutAddress;
import br.com.projetoferias.model.OrderSummary;
import br.com.projetoferias.model.UserAccount;
import br.com.projetoferias.service.OrderService;
import br.com.projetoferias.service.ProductService;
import br.com.projetoferias.service.ShoppingSessionService;
import br.com.projetoferias.service.ViaCepService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> pay(@RequestBody Map<String, String> body, HttpSession session) {
        UserAccount user = (UserAccount) session.getAttribute("user");
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "Nao autenticado."));
        if (shoppingSession.cartCount() == 0) return ResponseEntity.badRequest().body(Map.of("error", "Carrinho vazio."));

        return viaCepService.findAddress(body.get("cep"))
                .map(address -> {
                    CheckoutAddress fullAddress = new CheckoutAddress(
                            address.cep(),
                            body.getOrDefault("rua", address.logradouro()),
                            address.bairro(),
                            address.localidade(),
                            address.uf(),
                            body.getOrDefault("numero", ""),
                            body.getOrDefault("complemento", "")
                    );
                    int installments = Integer.parseInt(body.getOrDefault("installments", "1"));
                    OrderSummary order = orderService.createOrder(shoppingSession, productService, fullAddress, body.get("paymentMethod"), installments);
                    shoppingSession.clearCart();
                    return ResponseEntity.ok(order);
                })
                .orElse(ResponseEntity.badRequest().build());
    }
}