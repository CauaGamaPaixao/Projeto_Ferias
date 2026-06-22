package br.com.projetoferias.service;

import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;
import br.com.projetoferias.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void filtersProductsByCategory() {

        Product chuteira1 = new Product(
                1L,
                "Chuteira Nike Mercurial Rosa",
                "Nike",
                ProductCategory.CHUTEIRAS,
                "Descrição",
                BigDecimal.valueOf(399.90),
                "img.jpg"
        );

        Product chuteira2 = new Product(
                2L,
                "Chuteira Puma Future",
                "Puma",
                ProductCategory.CHUTEIRAS,
                "Descrição",
                BigDecimal.valueOf(349.90),
                "img.jpg"
        );

        when(productRepository.findAll())
                .thenReturn(List.of(chuteira1, chuteira2));

        assertThat(productService.findAll(null, ProductCategory.CHUTEIRAS))
                .hasSize(2)
                .allMatch(product -> product.category() == ProductCategory.CHUTEIRAS);
    }

    @Test
    void searchesProductsByText() {

        Product produto = new Product(
                1L,
                "Chuteira Nike Mercurial Rosa",
                "Nike",
                ProductCategory.CHUTEIRAS,
                "Descrição",
                BigDecimal.valueOf(399.90),
                "img.jpg"
        );

        when(productRepository.findAll())
                .thenReturn(List.of(produto));

        assertThat(productService.findAll("mercurial", null))
                .extracting(Product::name)
                .contains("Chuteira Nike Mercurial Rosa");
    }
}