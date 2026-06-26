package br.com.projetoferias.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderSummary(
        String code,
        List<CartItem> items,
        BigDecimal total,
        CheckoutAddress address,
        String paymentMethod,
        LocalDateTime createdAt
) {
}
