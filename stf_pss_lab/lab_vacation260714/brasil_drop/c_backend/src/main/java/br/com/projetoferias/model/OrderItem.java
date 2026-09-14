package br.com.projetoferias.model;

import java.math.BigDecimal;

public record OrderItem(Long productId, String name, BigDecimal unitPrice,
                        int quantity, BigDecimal subtotal) {
    public static OrderItem from(CartItem item) {
        return new OrderItem(item.product().id(), item.product().name(), item.product().price(),
                item.quantity(), item.subtotal());
    }
}
