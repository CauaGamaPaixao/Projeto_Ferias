package br.com.projetoferias.service;

import br.com.projetoferias.model.*;
import br.com.projetoferias.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) { this.repository = repository; }

    public OrderSummary createOrder(UserAccount user, List<CartItem> cart, CheckoutAddress address,
                                    String paymentMethod, int installments) {
        if (cart.isEmpty() || cart.stream().anyMatch(item -> item.quantity() <= 0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carrinho vazio ou inválido.");
        }
        int limit = switch (paymentMethod) {
            case "PIX" -> 1;
            case "CARTAO_CREDITO" -> 10;
            case "BOLETO" -> 6;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pagamento inválido.");
        };
        if (installments < 1 || installments > limit) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parcelamento inválido.");
        }
        List<OrderItem> items = cart.stream().map(OrderItem::from).toList();
        BigDecimal total = items.stream().map(OrderItem::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        OrderSummary order = new OrderSummary("BD-" + UUID.randomUUID().toString().replace("-", "").toUpperCase(),
                user.email(), user.name(), items, total, address, paymentMethod, installments,
                OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MICROS));
        // save() commits every snapshot before the controller clears the session cart.
        repository.save(order);
        return order;
    }

    public OrderSummary findForUser(String code, UserAccount user) {
        OrderSummary order = repository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!order.ownerEmail().equals(user.email())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return order;
    }

    public List<OrderSummary> listForUser(UserAccount user) { return repository.findByOwner(user.email()); }
}
