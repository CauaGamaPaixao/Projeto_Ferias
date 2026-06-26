package br.com.projetoferias.model;

import java.math.BigDecimal;

public record CartItem(Product product, int quantity) {

    public BigDecimal subtotal() {
        return product.price().multiply(BigDecimal.valueOf(quantity));
    }
}
