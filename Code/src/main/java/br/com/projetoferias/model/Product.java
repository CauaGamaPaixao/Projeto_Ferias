package br.com.projetoferias.model;

import java.math.BigDecimal;

public record Product(
        Long id,
        String name,
        String brand,
        ProductCategory category,
        String description,
        BigDecimal price,
        String imageUrl
) {
}
