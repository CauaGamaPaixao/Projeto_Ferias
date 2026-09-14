package br.com.projetoferias.controller.dto;

import br.com.projetoferias.model.CheckoutAddress;
import br.com.projetoferias.model.OrderItem;
import br.com.projetoferias.model.OrderSummary;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderResponse(String code, String buyerName, List<OrderItem> items, BigDecimal total,
                            CheckoutAddress address, String paymentMethod, int installments,
                            OffsetDateTime createdAt) {
    public static OrderResponse from(OrderSummary order) {
        return new OrderResponse(order.code(), order.buyerName(), order.items(), order.total(),
                order.address(), order.paymentMethod(), order.installments(), order.createdAt());
    }
}
