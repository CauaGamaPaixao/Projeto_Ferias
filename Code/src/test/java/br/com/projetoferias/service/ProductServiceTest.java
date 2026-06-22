package br.com.projetoferias.service;

import br.com.projetoferias.model.ProductCategory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {

    private final ProductService productService = new ProductService();

    @Test
    void filtersProductsByCategory() {
        assertThat(productService.findAll(null, ProductCategory.CHUTEIRAS))
                .allMatch(product -> product.category() == ProductCategory.CHUTEIRAS)
                .hasSize(2);
    }

    @Test
    void searchesProductsByText() {
        assertThat(productService.findAll("mercurial", null))
                .extracting("name")
                .contains("Chuteira Nike Mercurial Rosa");
    }
}
