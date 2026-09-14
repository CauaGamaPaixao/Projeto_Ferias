package br.com.projetoferias.controller;

import br.com.projetoferias.controller.dto.OrderResponse;
import br.com.projetoferias.model.UserAccount;
import br.com.projetoferias.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orders;
    public OrderController(OrderService orders) { this.orders = orders; }

    @GetMapping("/{code}")
    public ResponseEntity<OrderResponse> get(@PathVariable String code, HttpSession session) {
        return ResponseEntity.ok().header("Cache-Control", "no-store")
                .body(OrderResponse.from(orders.findForUser(code, user(session))));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> list(HttpSession session) {
        return ResponseEntity.ok().header("Cache-Control", "no-store")
                .body(orders.listForUser(user(session)).stream().map(OrderResponse::from).toList());
    }

    private UserAccount user(HttpSession session) {
        UserAccount user = (UserAccount) session.getAttribute("user");
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return user;
    }
}
