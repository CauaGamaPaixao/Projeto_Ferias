package br.com.projetoferias.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderSummary(
        String code,
        String ownerEmail,
        String buyerName,
        List<OrderItem> items,
        BigDecimal total,
        CheckoutAddress address,
        String paymentMethod,
        int installments,
        OffsetDateTime createdAt
) {
    public OrderSummary {
        items = List.copyOf(items);
    }
}
